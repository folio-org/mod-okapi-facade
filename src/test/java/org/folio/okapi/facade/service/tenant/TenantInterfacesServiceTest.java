package org.folio.okapi.facade.service.tenant;

import static java.util.List.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import org.folio.common.domain.model.InterfaceDescriptor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.common.domain.model.RoutingEntry;
import org.folio.okapi.facade.integration.ma.ApplicationManagerClient;
import org.folio.okapi.facade.integration.ma.model.ApplicationDescriptor;
import org.folio.okapi.facade.integration.model.ResultList;
import org.folio.okapi.facade.integration.mte.TenantEntitlementClient;
import org.folio.okapi.facade.integration.mte.model.Entitlement;
import org.folio.okapi.facade.integration.tm.TenantManagerClient;
import org.folio.okapi.facade.integration.tm.model.Tenant;
import org.folio.okapi.facade.mapper.InterfaceDescriptorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TenantInterfacesServiceTest {

  @InjectMocks TenantInterfacesService unit;
  @Mock ApplicationManagerClient applicationManagerClient;
  @Mock TenantEntitlementClient tenantEntitlementClient;
  @Mock TenantManagerClient tenantManagerClient;
  @Spy InterfaceDescriptorMapper mapper = Mappers.getMapper(InterfaceDescriptorMapper.class);

  @Test
  void getTenantInterfaces_positive_interfacesReturned() {
    var tenantName = "tenant123";
    var tenantId = randomUUID();
    when(tenantManagerClient.queryTenantsByName(eq(tenantName), any())).thenReturn(
      ResultList.of(1, of(Tenant.of(tenantId, tenantName, tenantName))));
    when(tenantEntitlementClient.findByQuery(eq("tenantId==" + tenantId), anyInt(), anyInt(), any())).thenReturn(
      ResultList.of(3, of(Entitlement.of("app1", tenantId.toString()), Entitlement.of("app2", tenantId.toString()),
        Entitlement.of("app3", tenantId.toString()))));
    var mockData = new ModuleDescriptor();
    mockData.setProvides(of(new InterfaceDescriptor()));
    var routingEntry = new RoutingEntry();
    routingEntry.setMethods(of("GET"));
    routingEntry.setPath("/hello/world");
    routingEntry.setType("system");
    mockData.getProvides().get(0).setId("provider1");
    mockData.getProvides().get(0).setVersion("ver1");
    mockData.getProvides().get(0).setHandlers(of(routingEntry));
    mockData.getProvides().get(0).setInterfaceType("system");
    when(applicationManagerClient.queryApplicationDescriptors(eq("id==app1 OR id==app2 OR id==app3"), anyBoolean(),
      anyInt(), anyInt(), any())).thenReturn(
      ResultList.of(1, of(ApplicationDescriptor.builder().moduleDescriptors(of(mockData)).build())));
    var result = unit.getTenantInterfaces("user token", tenantName, true, "system");
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getId()).isEqualTo(mockData.getProvides().get(0).getId());
    assertThat(result.get(0).getVersion()).isEqualTo(mockData.getProvides().get(0).getVersion());

    assertThat(result.get(0).getHandlers()).hasSize(1);
    assertThat(result.get(0).getHandlers().get(0).getMethods()).isEqualTo(of("GET"));
    assertThat(result.get(0).getHandlers().get(0).getPath()).isEqualTo("/hello/world");
  }

  @Test
  void getTenantInterfaces_negative_tenantMissing() {
    when(tenantManagerClient.queryTenantsByName(any(), any())).thenReturn(ResultList.of(0, of()));
    assertThatThrownBy(() -> unit.getTenantInterfaces("user token", "missing tenant", true, "system")).hasMessage(
      "Tenant not found by name missing tenant").hasSameClassAs(new EntityNotFoundException());
  }
}
