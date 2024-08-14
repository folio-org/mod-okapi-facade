package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.okapi.facade.domain.dto.DeploymentDescriptor;
import org.folio.okapi.facade.domain.dto.NodeDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenDeploymentDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenNodeDescriptor;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(DiscoveryController.class)
@ExtendWith(InstancioExtension.class)
class DiscoveryControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void createDiscovery_negative_notImplemented(@GivenDeploymentDescriptor DeploymentDescriptor descriptor)
    throws Exception {
    mockMvc.perform(post("/_/discovery/modules")
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteAllDiscoveries_negative_notImplemented() throws Exception {
    mockMvc.perform(delete("/_/discovery/modules"))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteDiscoveriesByService_negative_notImplemented(@Given String serviceId) throws Exception {
    mockMvc.perform(delete("/_/discovery/modules/{serviceId}", serviceId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteDiscoveryByInstance_negative_notImplemented(@Given String serviceId, @Given String instanceId)
    throws Exception {
    mockMvc.perform(delete("/_/discovery/modules/{service_id}/{instance_id}", serviceId, instanceId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllDiscoveries_negative_notImplemented() throws Exception {
    mockMvc.perform(get("/_/discovery/modules")
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllDiscoveryHealth_negative_notImplemented() throws Exception {
    mockMvc.perform(get("/_/discovery/health")
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllDiscoveryNodes_negative_notImplemented() throws Exception {
    mockMvc.perform(get("/_/discovery/nodes")
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getDiscoveriesByService_negative_notImplemented(@Given String serviceId) throws Exception {
    mockMvc.perform(get("/_/discovery/modules/{serviceId}", serviceId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getDiscoveryByInstance_negative_notImplemented(@Given String serviceId, @Given String instanceId)
    throws Exception {
    mockMvc.perform(get("/_/discovery/modules/{serviceId}/{instanceId}", serviceId, instanceId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getDiscoveryHealthByInstance_negative_notImplemented(@Given String serviceId, @Given String instanceId)
    throws Exception {
    mockMvc.perform(get("/_/discovery/health/{serviceId}/{instanceId}", serviceId, instanceId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getDiscoveryHealthByService_negative_notImplemented(@Given String serviceId) throws Exception {
    mockMvc.perform(get("/_/discovery/health/{serviceId}", serviceId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getDiscoveryNode_negative_notImplemented(@Given String nodeId) throws Exception {
    mockMvc.perform(get("/_/discovery/nodes/{nodeId}", nodeId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void updateDiscoveryNode_negative_notImplemented(@GivenNodeDescriptor NodeDescriptor descriptor) throws Exception {
    mockMvc.perform(put("/_/discovery/nodes/{nodeId}", descriptor.getNodeId())
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }
}
