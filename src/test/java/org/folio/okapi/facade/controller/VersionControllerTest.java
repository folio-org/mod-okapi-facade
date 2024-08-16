package org.folio.okapi.facade.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.test.types.UnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(VersionController.class)
class VersionControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void getVersion() throws Exception {
    mockMvc.perform(get("/_/version"))
      .andExpect(status().isNotImplemented());
  }
}
