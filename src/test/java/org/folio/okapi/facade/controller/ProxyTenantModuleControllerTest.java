package org.folio.okapi.facade.controller;

import static java.lang.Boolean.TRUE;
import static org.folio.okapi.facade.support.DescriptorUtils.moduleDescriptors;
import static org.folio.test.TestUtils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.domain.dto.TenantModuleDescriptor;
import org.folio.okapi.facade.service.TenantModuleService;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTenantModuleDescriptor;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(ProxyTenantModuleController.class)
@ExtendWith(InstancioExtension.class)
class ProxyTenantModuleControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private TenantModuleService service;

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
  void getAllTenantModules_positive(@Given String tenantId) throws Exception {
    List<ModuleDescriptor> descriptors = moduleDescriptors(10);
    when(service.findAll(tenantId, null, false, null, null, null, null, null, null, TRUE.toString(), TRUE.toString()))
      .thenReturn(descriptors);

    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/modules", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andExpect(content().contentType(APPLICATION_JSON))
      .andExpect(content().json(asJsonString(descriptors), true));
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
