package org.folio.okapi.facade.controller;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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
@WebMvcTest(ProxyTenantInterfaceController.class)
@ExtendWith(InstancioExtension.class)
class ProxyTenantInterfaceControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void getAllInterfaces_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}//interfaces", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getInterface_negative_notImplemented(@Given String tenantId, @Given String interfaceId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/interfaces/{interfaceId}", tenantId, interfaceId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }
}
