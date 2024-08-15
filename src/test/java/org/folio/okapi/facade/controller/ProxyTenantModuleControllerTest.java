package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@WebMvcTest(ProxyTenantModuleController.class)
@ExtendWith(InstancioExtension.class)
class ProxyTenantModuleControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void disableTenantModule_negative_notImplemented(@Given String tenantId, @Given String moduleId) throws Exception {
    mockMvc.perform(delete("/_/proxy/tenants/{tenantId}/modules/{moduleId}", tenantId, moduleId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void disableTenantModules_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(delete("/_/proxy/tenants/{tenantId}/modules", tenantId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void enableTenantModule_negative_notImplemented(@GivenTenantModuleDescriptor TenantModuleDescriptor descriptor,
    @Given String tenantId) throws Exception {
    mockMvc.perform(post("/_/proxy/tenants/{tenantId}/modules", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllTenantModules_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/modules", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getTenantModule_negative_notImplemented(@Given String tenantId, @Given String moduleId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/modules/{moduleId}", tenantId, moduleId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void upgradeTenantModule_negative_notImplemented(@GivenTenantModuleDescriptor TenantModuleDescriptor descriptor,
    @Given String tenantId, @Given String moduleId) throws Exception {
    mockMvc.perform(post("/_/proxy/tenants/{tenantId}/modules/{moduleId}", tenantId, moduleId)
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }
}
