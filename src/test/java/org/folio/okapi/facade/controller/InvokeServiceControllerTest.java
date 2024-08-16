package org.folio.okapi.facade.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(InvokeServiceController.class)
@ExtendWith(InstancioExtension.class)
class InvokeServiceControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void invoke_negative_notImplemented(@Given String id) throws Exception {
    mockMvc.perform(get("/_/invoke/tenant/{id}", id))
      .andExpect(status().isNotImplemented());
  }
}
