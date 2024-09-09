package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.common.domain.model.RoutingEntry;
import org.folio.okapi.facade.domain.dto.TimerDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenRoutingEntry;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTimerDescriptor;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(ProxyTenantTimerController.class)
@ExtendWith(InstancioExtension.class)
class ProxyTenantTimerControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void getAllTimers_negative_notImplemented(@Given String tenantId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/timers", tenantId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getTimer_negative_notImplemented(@Given String tenantId, @Given String timerId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/timers/{timerId}", tenantId, timerId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void updateTimer_negative_notImplemented(@GivenRoutingEntry RoutingEntry routingEntry,
    @Given String tenantId, @Given String timerId) throws Exception {
    mockMvc.perform(patch("/_/proxy/tenants/{tenantId}/timers/{timerId}", tenantId, timerId)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(routingEntry)))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void updateTimers_negative_notImplemented(@GivenTimerDescriptor TimerDescriptor descriptor,
    @Given String tenantId) throws Exception {
    mockMvc.perform(patch("/_/proxy/tenants/{tenantId}/timers", tenantId)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }
}
