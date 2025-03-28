package org.folio.okapi.facade.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.folio.okapi.facade.integration.IntegrationException;
import org.folio.spring.exception.NotFoundException;
import org.folio.test.types.UnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@UnitTest
@WebMvcTest(ApiExceptionHandlerTest.TestController.class)
@Import({ApiExceptionHandler.class, ApiExceptionHandlerTest.TestController.class})
class ApiExceptionHandlerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private TestService testService;

  @Test
  void handleIntegrationException_positive() throws Exception {
    var cause = new RuntimeException("Server unavailable");
    when(testService.getTestValue()).thenThrow(new IntegrationException("Failed to perform request", cause));
    mockMvc.perform(get("/tests").queryParam("query", "cql.allRecords=1").contentType(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is("Failed to perform request")))
      .andExpect(jsonPath("$.errors[0].type", is("IntegrationException")))
      .andExpect(jsonPath("$.errors[0].code", is("service_error")))
      .andExpect(jsonPath("$.errors[0].parameters[0].key", is("cause")))
      .andExpect(jsonPath("$.errors[0].parameters[0].value", is("Server unavailable")));
  }

  @Test
  void handleEntityNotFoundException_positive() throws Exception {
    when(testService.getTestValue()).thenThrow(new NotFoundException("Entity not found"));
    mockMvc.perform(get("/tests").queryParam("query", "cql.allRecords=1").contentType(APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is("Entity not found")))
      .andExpect(jsonPath("$.errors[0].type", is("NotFoundException")))
      .andExpect(jsonPath("$.errors[0].code", is("not_found_error")));
  }

  @Test
  void handleAllOtherExceptions_positive() throws Exception {
    when(testService.getTestValue()).thenThrow(new NullPointerException());
    mockMvc.perform(get("/tests")
        .queryParam("query", "cql.allRecords=1")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isInternalServerError())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].type", is("NullPointerException")))
      .andExpect(jsonPath("$.errors[0].code", is("unknown_error")));
  }

  @Test
  void handleRuntimeError_positive() throws Exception {
    when(testService.getTestValue()).thenThrow(new RuntimeException("error"));
    mockMvc.perform(get("/tests")
        .queryParam("query", "cql.allRecords=1")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isInternalServerError())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is("error")))
      .andExpect(jsonPath("$.errors[0].type", is("RuntimeException")))
      .andExpect(jsonPath("$.errors[0].code", is("unknown_error")));
  }

  @Test
  void handleConstraintViolationException_positive() throws Exception {
    mockMvc.perform(get("/tests")
        .queryParam("limit", "10000")
        .queryParam("query", "cql.allRecords=1")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is("getTests.limit must be less than or equal to 500")))
      .andExpect(jsonPath("$.errors[0].type", is("ConstraintViolationException")))
      .andExpect(jsonPath("$.errors[0].code", is("validation_error")));
  }

  @Test
  void handleMissingServletRequestParameterException_positive() throws Exception {
    var errorMessage = "Required request parameter 'query' for method parameter type String is not present";
    mockMvc.perform(get("/tests").queryParam("limit", "10000").contentType(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is(errorMessage)))
      .andExpect(jsonPath("$.errors[0].type", is("MissingServletRequestParameterException")))
      .andExpect(jsonPath("$.errors[0].code", is("validation_error")));
  }

  @Test
  void handleValidationExceptions_positive_httpMediaTypeNotSupportedException() throws Exception {
    mockMvc.perform(get("/tests").contentType(TEXT_PLAIN))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is("Content-Type 'text/plain' is not supported")))
      .andExpect(jsonPath("$.errors[0].type", is("HttpMediaTypeNotSupportedException")))
      .andExpect(jsonPath("$.errors[0].code", is("validation_error")));
  }

  @Test
  void handleMethodArgumentNotValidException_positive() throws Exception {
    mockMvc.perform(put("/tests/{id}", UUID.randomUUID())
        .content("{\"key\": \"value\"}")
        .contentType(APPLICATION_JSON)
        .header(CACHE_CONTROL, "no-cache"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is("must not be null")))
      .andExpect(jsonPath("$.errors[0].type", is("MethodArgumentNotValidException")))
      .andExpect(jsonPath("$.errors[0].code", is("validation_error")))
      .andExpect(jsonPath("$.errors[0].parameters[0].key", is("id")))
      .andExpect(jsonPath("$.errors[0].parameters[0].value", is("null")));
  }

  @Test
  void handleValidationExceptions_positive_headerIsMissing() throws Exception {
    var errorMessage = "Required request header 'Cache-Control' for method parameter type String is not present";
    mockMvc.perform(put("/tests/{id}", UUID.randomUUID())
        .content("{\"id\": \"8edfff61-d2c8-401b-afed-6348a9d855b2\"}")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", is(errorMessage)))
      .andExpect(jsonPath("$.errors[0].type", is("MissingRequestHeaderException")))
      .andExpect(jsonPath("$.errors[0].code", is("validation_error")));
  }

  @Test
  void handleValidationExceptions_positive_invalidIdInPath() throws Exception {
    var errorMessage = "Failed to convert value of type 'java.lang.String' to required type 'java.util.UUID'";
    mockMvc.perform(put("/tests/{id}", "resource-id")
        .content("{\"id\": \"8edfff61-d2c8-401b-afed-6348a9d855b2\"}")
        .contentType(APPLICATION_JSON)
        .header(CACHE_CONTROL, "no-cache"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", containsString(errorMessage)))
      .andExpect(jsonPath("$.errors[0].type", is("MethodArgumentTypeMismatchException")))
      .andExpect(jsonPath("$.errors[0].code", is("validation_error")));
  }

  @Test
  void handleHttpMessageNotReadableException_positive() throws Exception {
    mockMvc.perform(put("/tests/{id}", UUID.randomUUID())
        .content("\"key\": \"8edfff61-d2c8-401b-afed-6348a9d855b2\"}")
        .contentType(APPLICATION_JSON)
        .header(CACHE_CONTROL, "no-cache"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.total_records", is(1)))
      .andExpect(jsonPath("$.errors[0].message", startsWith("JSON parse error: Cannot construct instance of")))
      .andExpect(jsonPath("$.errors[0].type", is("HttpMessageNotReadableException")))
      .andExpect(jsonPath("$.errors[0].code", is("validation_error")));
  }

  @Validated
  @RestController
  @RequiredArgsConstructor
  static class TestController {

    private final TestService testService;

    @SuppressWarnings("unused")
    @GetMapping(value = "/tests", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTests(
      @RequestParam(value = "query") String query,
      @Min(0) @Valid @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
      @Min(0) @Max(500) @Valid @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
      return ResponseEntity.ok(testService.getTestValue());
    }

    @SuppressWarnings("unused")
    @PutMapping(value = "/tests/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putTest(
      @PathVariable(value = "id") UUID id,
      @Valid @RequestBody TestRequest request,
      @RequestHeader(CACHE_CONTROL) String cacheControlHeader) {
      return ResponseEntity.ok(testService.getTestValue());
    }
  }

  static class TestService {

    public Object getTestValue() {
      return "test value";
    }
  }

  @Data
  private static final class TestRequest {

    @NotNull private UUID id;
  }
}

