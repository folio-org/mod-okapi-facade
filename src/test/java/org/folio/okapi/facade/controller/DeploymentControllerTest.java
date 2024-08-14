package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.okapi.facade.domain.dto.DeploymentDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenDeploymentDescriptor;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(DeploymentController.class)
@ExtendWith(InstancioExtension.class)
class DeploymentControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void deleteDeployedModuleInstance_negative_notImplemented(@Given String instanceId) throws Exception {
    mockMvc.perform(delete("/_/deployment/modules/{instanceId}", instanceId)
        .contentType(APPLICATION_JSON))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deployModuleInstance_negative_notImplemented(@GivenDeploymentDescriptor DeploymentDescriptor descriptor)
    throws Exception {
    mockMvc.perform(post("/_/deployment/modules")
        .header(HttpHeaders.ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllDeployedModuleInstances() throws Exception {
    mockMvc.perform(get("/_/deployment/modules")
        .header(HttpHeaders.ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getDeployedModuleInstance(@Given String instanceId) throws Exception {
    mockMvc.perform(get("/_/deployment/modules/{instanceId}", instanceId)
        .header(HttpHeaders.ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }
}