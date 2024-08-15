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

import org.folio.okapi.facade.domain.dto.TenantDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTenantDescriptor;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(ProxyTenantController.class)
@ExtendWith(InstancioExtension.class)
class ProxyTenantControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void createTenant_negative_notImplemented(@GivenTenantDescriptor TenantDescriptor descriptor) throws Exception {
    mockMvc.perform(post("/_/proxy/tenants")
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteTenant_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(delete("/_/proxy/tenants/{tenantId}", tenantId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllTenants_negative_notImplemented() throws Exception {
    mockMvc.perform(get("/_/proxy/tenants")
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getTenant_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void updateTenant_negative_notImplemented(@GivenTenantDescriptor TenantDescriptor descriptor) throws Exception {
    mockMvc.perform(put("/_/proxy/tenants/{tenantId}", descriptor.getId())
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }
}
