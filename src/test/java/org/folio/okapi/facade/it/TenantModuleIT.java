package org.folio.okapi.facade.it;

import static org.folio.test.TestConstants.TENANT_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.folio.okapi.facade.base.BaseIntegrationTest;
import org.folio.test.extensions.WireMockStub;
import org.folio.test.types.IntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@IntegrationTest
public class TenantModuleIT extends BaseIntegrationTest {

  @BeforeAll
  static void beforeAll() {
    enableTenant(TENANT_ID);
  }

  @AfterAll
  static void afterAll() {
    removeTenant(TENANT_ID);
  }

  @Test
  @WireMockStub("/wiremock/stubs/mte/find-tenant-entitled-applications.json")
  void findAll_positive() throws Exception {
    attemptGet("/_/proxy/tenants/{tenantId}/modules", TENANT_ID)
      .andDo(logResponseBody())
      .andExpect(status().isOk());
  }
}
