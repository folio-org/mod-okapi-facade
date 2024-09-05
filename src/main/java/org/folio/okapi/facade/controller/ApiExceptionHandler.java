package org.folio.okapi.facade.controller;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;
import static org.folio.common.domain.model.error.ErrorCode.NOT_FOUND_ERROR;
import static org.folio.common.domain.model.error.ErrorCode.SERVICE_ERROR;
import static org.folio.common.domain.model.error.ErrorCode.UNKNOWN_ERROR;
import static org.folio.common.domain.model.error.ErrorCode.VALIDATION_ERROR;
import static org.folio.common.utils.ExceptionHandlerUtils.buildErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.folio.common.domain.model.error.Error;
import org.folio.common.domain.model.error.ErrorCode;
import org.folio.common.domain.model.error.ErrorResponse;
import org.folio.common.domain.model.error.Parameter;
import org.folio.okapi.facade.integration.IntegrationException;
import org.folio.spring.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
  /**
   * Catches and handles all exceptions for type {@link NotFoundException} or {@link FeignException.NotFound}.
   *
   * @param exception {@link NotFoundException} / {@link FeignException.NotFound} object
   * @return {@link ResponseEntity} with {@link ErrorResponse} body.
   */
  @ExceptionHandler({NotFoundException.class, FeignException.NotFound.class})
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception exception) {
    logException(DEBUG, exception);
    return buildResponseEntity(exception, NOT_FOUND, NOT_FOUND_ERROR);
  }

  /**
   * Catches and handles all exceptions for type {@link HttpMessageNotReadableException}.
   *
   * @param exception {@link HttpMessageNotReadableException} object
   * @return {@link ResponseEntity} with {@link ErrorResponse} body.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handlerHttpMessageNotReadableException(
    HttpMessageNotReadableException exception) {

    return Optional.ofNullable(exception.getCause())
      .map(Throwable::getCause)
      .filter(IllegalArgumentException.class::isInstance)
      .map(IllegalArgumentException.class::cast)
      .map(this::handleValidationExceptions)
      .orElseGet(() -> {
        logException(DEBUG, exception);
        return buildResponseEntity(exception, BAD_REQUEST, VALIDATION_ERROR);
      });
  }

  /**
   * Catches and handles all exceptions for type {@link MissingServletRequestParameterException}.
   *
   * @param exception {@link MissingServletRequestParameterException} to process
   * @return {@link ResponseEntity} with {@link ErrorResponse} body
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
    MissingServletRequestParameterException exception) {
    logException(DEBUG, exception);
    return buildResponseEntity(exception, BAD_REQUEST, VALIDATION_ERROR);
  }

  /**
   * Catches and handles all exceptions for type {@link org.springframework.web.bind.MethodArgumentNotValidException}.
   *
   * @param e {@link org.springframework.web.bind.MethodArgumentNotValidException} to process
   * @return {@link org.springframework.http.ResponseEntity} with {@link ErrorResponse} body
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    logException(DEBUG, e);
    var validationErrors = Optional.of(e.getBindingResult()).map(Errors::getAllErrors).orElse(emptyList());
    var errorResponse = new ErrorResponse();
    validationErrors.forEach(error ->
      errorResponse.addErrorsItem(new Error()
        .message(error.getDefaultMessage())
        .code(ErrorCode.VALIDATION_ERROR)
        .type(MethodArgumentNotValidException.class.getSimpleName())
        .addParametersItem(new Parameter()
          .key(((FieldError) error).getField())
          .value(String.valueOf(((FieldError) error).getRejectedValue())))));
    errorResponse.totalRecords(errorResponse.getErrors().size());

    return buildResponseEntity(errorResponse, BAD_REQUEST);
  }

  /**
   * Catches and handles all exceptions for type {@link jakarta.validation.ConstraintViolationException}.
   *
   * @param exception {@link jakarta.validation.ConstraintViolationException} to process
   * @return {@link org.springframework.http.ResponseEntity} with {@link ErrorResponse} body
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {
    logException(DEBUG, exception);
    var errorResponse = new ErrorResponse();
    exception.getConstraintViolations().forEach(constraintViolation ->
      errorResponse.addErrorsItem(new Error()
        .message(String.format("%s %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
        .code(VALIDATION_ERROR)
        .type(ConstraintViolationException.class.getSimpleName())));
    errorResponse.totalRecords(errorResponse.getErrors().size());

    return buildResponseEntity(errorResponse, BAD_REQUEST);
  }

  /**
   * Catches and handles common request validation exceptions.
   *
   * @param exception {@link Exception} object to process
   * @return {@link org.springframework.http.ResponseEntity} with {@link ErrorResponse} body
   */
  @ExceptionHandler({
    IllegalArgumentException.class,
    MissingRequestHeaderException.class,
    HttpMediaTypeNotSupportedException.class,
    MethodArgumentTypeMismatchException.class,
    FeignException.BadRequest.class
  })
  public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception exception) {
    logException(DEBUG, exception);
    return buildResponseEntity(exception, BAD_REQUEST, VALIDATION_ERROR);
  }

  /**
   * Catches and handles all exceptions for type {@link IntegrationException}.
   *
   * @param exception {@link IntegrationException} to process
   * @return {@link ResponseEntity} with {@link ErrorResponse} body
   */
  @ExceptionHandler(IntegrationException.class)
  public ResponseEntity<ErrorResponse> handleIntegrationException(Exception exception) {
    logException(WARN, exception);
    var errorParameters = singletonList(new Parameter().key("cause").value(exception.getCause().getMessage()));
    var errorResponse = buildErrorResponse(exception, errorParameters, SERVICE_ERROR);
    return buildResponseEntity(errorResponse, BAD_REQUEST);
  }

  /**
   * Handles all uncaught exceptions.
   *
   * @param exception {@link Exception} object
   * @return {@link ResponseEntity} with {@link ErrorResponse} body.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception exception) {
    logException(WARN, exception);
    return buildResponseEntity(exception, INTERNAL_SERVER_ERROR, UNKNOWN_ERROR);
  }

  private static ResponseEntity<ErrorResponse> buildResponseEntity(Exception error, HttpStatus status, ErrorCode code) {
    var errorResponse = new ErrorResponse()
      .errors(List.of(new Error()
        .message(error.getMessage())
        .type(error.getClass().getSimpleName())
        .code(code)))
      .totalRecords(1);

    return buildResponseEntity(errorResponse, status);
  }

  private static ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse, HttpStatus status) {
    return ResponseEntity.status(status).body(errorResponse);
  }

  private static void logException(Level level, Exception exception) {
    log.log(level, "Handling exception", exception);
  }
}
