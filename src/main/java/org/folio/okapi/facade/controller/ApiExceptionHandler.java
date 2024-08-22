package org.folio.okapi.facade.controller;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;
import static org.folio.common.domain.model.error.ErrorCode.NOT_FOUND_ERROR;
import static org.folio.common.domain.model.error.ErrorCode.UNKNOWN_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.folio.common.domain.model.error.Error;
import org.folio.common.domain.model.error.ErrorCode;
import org.folio.common.domain.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
  /**
   * Catches and handles all exceptions for type {@link EntityNotFoundException}.
   *
   * @param exception {@link EntityNotFoundException} object
   * @return {@link ResponseEntity} with {@link ErrorResponse} body.
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
    logException(DEBUG, exception);
    return buildResponseEntity(exception, NOT_FOUND, NOT_FOUND_ERROR);
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
