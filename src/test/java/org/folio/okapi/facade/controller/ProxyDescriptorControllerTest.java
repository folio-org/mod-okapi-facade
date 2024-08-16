package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.okapi.facade.domain.dto.PullDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenPullDescriptor;
import org.folio.test.types.UnitTest;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(ProxyDescriptorController.class)
@ExtendWith(InstancioExtension.class)
class ProxyDescriptorControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void pullDescriptors_negative_notImplemented(@GivenPullDescriptor PullDescriptor descriptor) throws Exception {
    mockMvc.perform(post("/_/proxy/pull/modules")
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }
}
