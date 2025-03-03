package org.folio.okapi.facade.it;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.ArrayUtils.toArray;
import static org.folio.test.TestConstants.TENANT_ID;
import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.base.BaseIntegrationTest;
import org.folio.test.extensions.WireMockStub;
import org.folio.test.types.IntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@IntegrationTest
class TenantModuleIT extends BaseIntegrationTest {

  private static final String TENANT_UNKNOWN = "unknown-tenant";

  @BeforeAll
  static void beforeAll() {
    enableTenant(TENANT_ID);
  }

  @AfterAll
  static void afterAll() {
    removeTenant(TENANT_ID);
  }

  @Test
  @WireMockStub("/wiremock/stubs/mte/find-tenant-applications.json")
  void findAll_positive() throws Exception {
    attemptGet("/_/proxy/tenants/{tenantId}/modules", TENANT_ID)
      .andExpect(status().isOk())
      .andExpect(jsonStrict("tenant-modules/enabled-modules-response.json"));
  }

  @Test
  @WireMockStub("/wiremock/stubs/mte/find-tenant-applications.json")
  void findAll_positive_fullInfo() throws Exception {
    attemptGet("/_/proxy/tenants/{tenantId}/modules", toArray(TENANT_ID),
      rb -> rb.queryParam("full", TRUE.toString()))
      .andExpect(status().isOk())
      .andExpect(jsonStrict("tenant-modules/enabled-modules-full-response.json"));
  }

  @Test
  @WireMockStub("/wiremock/stubs/mte/find-tenant-applications.json")
  void findAll_positive_withModuleFilter() throws Exception {
    var mds = toArray(new ModuleDescriptor().id("mod-configuration-5.10.0"));

    attemptGet("/_/proxy/tenants/{tenantId}/modules", toArray(TENANT_ID),
      rb -> rb.queryParam("filter", "mod-configuration"))
      .andExpect(status().isOk())
      .andExpect(content().json(asJsonString(mds), true));
  }

  @Test
  @WireMockStub("/wiremock/stubs/mte/find-tenant-applications.json")
  void findAll_positive_descendingOrder() throws Exception {
    attemptGet("/_/proxy/tenants/{tenantId}/modules", toArray(TENANT_ID),
      rb -> rb.queryParam("orderBy", "id")
        .queryParam("order", "desc"))
      .andExpect(status().isOk())
      .andExpect(jsonStrict("tenant-modules/enabled-modules-desc-order-response.json"));
  }

  @Test
  @WireMockStub("/wiremock/stubs/mte/find-tenant-applications-tenant-not-found.json")
  void findAll_negative_tenantNotFound() throws Exception {
    attemptGet("/_/proxy/tenants/{tenantId}/modules", TENANT_UNKNOWN)
      .andDo(logResponseBody())
      .andExpectAll(notFoundWithMsg("Tenant is not found by name: " + TENANT_UNKNOWN));
  }

  @Test
  @WireMockStub("/wiremock/stubs/mte/find-tenant-applications-using-system-token.json")
  void findAll_positive_systemToken() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/_/proxy/tenants/{tenantId}/modules", TENANT_ID)
        .headers(okapiHeaders())
        .header("X-System-Token", "system-token")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonStrict("/tenant-modules/enabled-modules-response.json"));
  }
}
