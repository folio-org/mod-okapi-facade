package org.folio.okapi.facade.base;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static org.folio.test.TestConstants.OKAPI_AUTH_TOKEN;
import static org.folio.test.TestConstants.TENANT_ID;
import static org.folio.test.TestUtils.asJsonString;
import static org.folio.test.TestUtils.readString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.Function;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.folio.spring.integration.XOkapiHeaders;
import org.folio.tenant.domain.dto.TenantAttributes;
import org.folio.test.base.BaseBackendIntegrationTest;
import org.folio.test.extensions.EnablePostgres;
import org.folio.test.extensions.EnableWireMock;
import org.folio.test.extensions.impl.WireMockAdminClient;
import org.folio.test.extensions.impl.WireMockExecutionListener;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Log4j2
@EnableWireMock
@EnablePostgres
@SpringBootTest
@ActiveProfiles("it")
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_CLASS)
@TestExecutionListeners(listeners = {WireMockExecutionListener.class},
  mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public abstract class BaseIntegrationTest extends BaseBackendIntegrationTest {

  protected static WireMockAdminClient wmAdminClient;

  private static final String MODULE_NAME = "mod-okapi-facade";

  @Autowired protected CacheManager cacheManager;

  @AfterEach
  void afterEach() {
    evictAllCaches();
  }

  public void evictAllCaches() {
    for (var cacheName : cacheManager.getCacheNames()) {
      requireNonNull(cacheManager.getCache(cacheName)).clear();
    }
  }

  public static ResultActions attemptGet(String uri, Object... args) throws Exception {
    return attemptGet(uri, args, identity());
  }

  public static ResultActions attemptGet(String uri, Object[] args,
    Function<MockHttpServletRequestBuilder, MockHttpServletRequestBuilder> additionalConfigurer) throws Exception {
    var requestBuilder = get(uri, args)
      .headers(okapiHeaders())
      .contentType(APPLICATION_JSON);

    return mockMvc.perform(additionalConfigurer.apply(requestBuilder));
  }

  protected static HttpHeaders okapiHeaders() {
    var headers = new HttpHeaders();
    headers.add(XOkapiHeaders.URL, wmAdminClient.getWireMockUrl());
    headers.add(XOkapiHeaders.TOKEN, OKAPI_AUTH_TOKEN);
    headers.add(XOkapiHeaders.TENANT, TENANT_ID);
    return headers;
  }

  protected static ResultMatcher jsonStrict(String filePath) {
    String path = "json/" + filePath;
    return MockMvcResultMatchers.content().json(readString(path), true);
  }

  @SneakyThrows
  protected static void enableTenant(String tenant) {
    enableTenant(tenant, new TenantAttributes());
  }

  @SneakyThrows
  protected static void enableTenant(String tenant, TenantAttributes tenantAttributes) {
    tenantAttributes.moduleTo(MODULE_NAME);
    mockMvc.perform(post("/_/tenant")
        .content(asJsonString(tenantAttributes))
        .contentType(APPLICATION_JSON)
        .header(XOkapiHeaders.TENANT, tenant))
      .andExpect(status().isNoContent());
  }

  @SneakyThrows
  protected static void removeTenant(String tenantId) {
    var tenantAttributes = new TenantAttributes().moduleFrom(MODULE_NAME).purge(true);
    mockMvc.perform(post("/_/tenant")
        .content(asJsonString(tenantAttributes))
        .contentType(APPLICATION_JSON)
        .header(XOkapiHeaders.TENANT, tenantId))
      .andExpect(status().isNoContent());
  }
}
