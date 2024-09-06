package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.tenant.domain.dto.TenantAttributes;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(TenantController.class)
@ExtendWith(InstancioExtension.class)
class TenantControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void deleteTenant_positive(@Given String operationId) throws Exception {
    mockMvc.perform(delete("/_/tenant/{operationId}", operationId))
      .andExpect(status().isNoContent());
  }

  @Test
  void getTenant_negative_notImplemented(@Given String operationId) throws Exception {
    mockMvc.perform(get("/_/tenant/{operationId}", operationId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void postTenant_positive(@Given TenantAttributes tenantAttributes) throws Exception {
    mockMvc.perform(post("/_/tenant")
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(tenantAttributes)))
      .andExpect(status().isNoContent());
  }
}
