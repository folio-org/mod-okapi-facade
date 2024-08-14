package org.folio.okapi.facade.controller;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.test.types.UnitTest;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(ProxyHealthController.class)
@ExtendWith(InstancioExtension.class)
class ProxyHealthControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void getHealthStatus_negative_notImplemented() throws Exception {
    mockMvc.perform(get("/_/proxy/health")
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }
}
