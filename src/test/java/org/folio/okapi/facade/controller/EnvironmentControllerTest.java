package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.okapi.facade.domain.dto.EnvEntry;
import org.folio.okapi.facade.support.EnvUtils.GivenEnvEntry;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(EnvironmentController.class)
@ExtendWith(InstancioExtension.class)
class EnvironmentControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void createEnvironmentEntry_negative_notImplemented(@GivenEnvEntry EnvEntry envEntry) throws Exception {
    mockMvc.perform(post("/_/env")
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(envEntry)))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteEnvironmentEntry_negative_notImplemented(@Given String id) throws Exception {
    mockMvc.perform(delete("/_/env/{id}", id))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllEnvironmentEntries_negative_notImplemented() throws Exception {
    mockMvc.perform(get("/_/env")
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getEnvironmentEntry_negative_notImplemented(@Given String id) throws Exception {
    mockMvc.perform(get("/_/env/{id}", id)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }
}
