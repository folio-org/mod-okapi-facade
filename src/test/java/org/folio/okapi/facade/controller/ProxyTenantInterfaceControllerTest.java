package org.folio.okapi.facade.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import feign.Client;
import java.util.List;
import java.util.UUID;
import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.InterfaceDescriptor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.common.domain.model.ResultList;
import org.folio.common.domain.model.RoutingEntry;
import org.folio.okapi.facade.integration.ma.MgrApplicationsClient;
import org.folio.okapi.facade.integration.mt.MgrTenantsClient;
import org.folio.okapi.facade.integration.mt.model.Tenant;
import org.folio.okapi.facade.integration.mte.MgrTenantEntitlementsClient;
import org.folio.okapi.facade.integration.mte.model.Entitlement;
import org.folio.okapi.facade.service.TenantInterfacesService;
import org.folio.spring.config.FolioSpringConfiguration;
import org.folio.spring.scope.FolioExecutionScopeConfig;
import org.folio.spring.service.TenantService;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest({ProxyTenantInterfaceController.class, TenantInterfacesService.class, ApiExceptionHandler.class})
@Import({FolioExecutionScopeConfig.class, FolioSpringConfiguration.class})
@ExtendWith(InstancioExtension.class)
class ProxyTenantInterfaceControllerTest {

  @MockBean MgrApplicationsClient mgrApplicationsClient;
  @MockBean MgrTenantsClient mgrTenantsClient;
  @MockBean MgrTenantEntitlementsClient mgrTenantEntitlementsClient;
  @MockBean TenantService tenantService;
  @MockBean Client feignHttpClient;

  @Autowired private MockMvc mockMvc;

  @Test
  void getAllInterfaces_negative_nonExistingTenant() throws Exception {
    String tenantName = "mytenant";
    when(mgrTenantsClient.queryTenantsByName(eq(tenantName), any())).thenReturn(ResultList.of(0, List.of()));
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/interfaces", tenantName).header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotFound());
  }

  @Test
  void getAllInterfaces_positive_interfacesReturned() throws Exception {
    var tenantName = "mytenant";
    var tenantId = UUID.randomUUID();
    when(mgrTenantsClient.queryTenantsByName(eq(tenantName), any())).thenReturn(
      ResultList.of(1, List.of(Tenant.of(tenantId, tenantName, tenantName))));
    when(mgrTenantEntitlementsClient.findByQuery(eq("tenantId==" + tenantId), anyInt(), anyInt(), any())).thenReturn(
      ResultList.of(3, List.of(Entitlement.of("app1", tenantId.toString()), Entitlement.of("app2", tenantId.toString()),
        Entitlement.of("app3", tenantId.toString()))));
    var result = new ModuleDescriptor();
    result.setProvides(List.of(new InterfaceDescriptor()));
    var routingEntry = new RoutingEntry();
    routingEntry.setMethods(List.of("GET"));
    routingEntry.setPath("/hello/world");
    routingEntry.setType("system");
    result.getProvides().get(0).setId("provider1");
    result.getProvides().get(0).setHandlers(List.of(routingEntry));
    result.getProvides().get(0).setInterfaceType("system");
    when(mgrApplicationsClient.queryApplicationDescriptors(eq("id==app1 OR id==app2 OR id==app3"), anyBoolean(),
      anyInt(), anyInt(), any())).thenReturn(
      ResultList.of(1, List.of(new ApplicationDescriptor().moduleDescriptors(List.of(result)))));
    mockMvc.perform(
        get("/_/proxy/tenants/{tenantId}/interfaces?full=true", tenantName).header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is("provider1")))
      .andExpect(jsonPath("$[0].id", is("provider1")))
      .andExpect(jsonPath("$[0].interfaceType", is("system")))
      .andExpect(jsonPath("$[0].handlers[0].type", is("system")))
      .andExpect(jsonPath("$[0].handlers[0].path", is("/hello/world")))
      .andExpect(jsonPath("$[0].handlers[0].methods[0]", is("GET")));
  }

  @Test
  void getInterface_negative_notImplemented(@Given String tenantId, @Given String interfaceId) throws Exception {
    mockMvc.perform(get("/_/proxy/tenants/{tenantId}/interfaces/{interfaceId}", tenantId, interfaceId).header(ACCEPT,
      APPLICATION_JSON_VALUE)).andExpect(status().isNotImplemented());
  }
}
