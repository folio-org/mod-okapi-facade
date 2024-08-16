package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;
import org.folio.okapi.facade.domain.dto.TenantModuleDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTenantModuleDescriptor;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(ProxyTenantInstallationController.class)
@ExtendWith(InstancioExtension.class)
class ProxyTenantInstallationControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void createInstallJob_negative_notImplemented(@GivenTenantModuleDescriptor Stream<TenantModuleDescriptor> descriptors,
    @Given String tenantId) throws Exception {
    mockMvc.perform(post("/_/proxy/tenants/{tenantId}/install", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptors.limit(10).toList())))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void createUpgradeJob_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(post("/_/proxy/tenants/{tenantId}/upgrade", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteInstallJob_negative_notImplemented(@Given String tenantId, @Given String installId) throws Exception {
    mockMvc.perform(delete("/_/proxy/tenants/{tenantId}/install/{installId}", tenantId, installId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteInstallJobs_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(delete("/_/proxy/tenants/{tenantId}/install", tenantId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllInstallJobs_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/install", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getInstallJob_negative_notImplemented(@Given String tenantId, @Given String installId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/install/{installId}", tenantId, installId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }
}
