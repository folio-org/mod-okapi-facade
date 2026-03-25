package org.folio.okapi.facade.integration;

import static org.folio.common.utils.OkapiHeaders.TOKEN;
import static org.folio.spring.integration.XOkapiHeaders.TENANT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;
import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ResultList;
import org.folio.okapi.facade.integration.mt.model.Tenant;
import org.folio.test.types.UnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@UnitTest
class HttpExchangeClientContractTest {

  private static final String BASE_URL = "https://mgr.example";
  private static final String TOKEN_VALUE = "token";
  private static final String TENANT_NAME = "mytenant";

  @Test
  void httpExchange_positive_supportsLegacyTenantQueryTemplate() {
    var restClientBuilder = RestClient.builder().baseUrl(BASE_URL);
    var mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
    var client = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClientBuilder.build()))
      .build()
      .createClient(TenantsQueryTemplateClient.class);

    mockServer.expect(request -> {
      var uri = request.getURI();
      org.junit.jupiter.api.Assertions.assertEquals(URI.create(BASE_URL).getScheme(), uri.getScheme());
      org.junit.jupiter.api.Assertions.assertEquals(URI.create(BASE_URL).getHost(), uri.getHost());
      org.junit.jupiter.api.Assertions.assertEquals("/tenants", uri.getPath());
      org.junit.jupiter.api.Assertions.assertEquals("query=name%3D%3D" + TENANT_NAME, uri.getRawQuery());
    })
      .andExpect(method(GET))
      .andExpect(header(TOKEN, TOKEN_VALUE))
      .andRespond(withSuccess("""
        {"totalRecords":0,"records":[]}
        """, MediaType.APPLICATION_JSON));

    client.queryTenantsByName(TENANT_NAME, TOKEN_VALUE);

    mockServer.verify();
  }

  @Test
  void httpExchange_positive_sendsTenantPathAndHeaderForEntitledApplications() {
    var restClientBuilder = RestClient.builder().baseUrl(BASE_URL);
    var mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
    var client = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClientBuilder.build()))
      .build()
      .createClient(EntitledApplicationsClient.class);

    mockServer.expect(requestTo(BASE_URL + "/entitlements/" + TENANT_NAME + "/applications?limit=500"))
      .andExpect(method(GET))
      .andExpect(header(TENANT, TENANT_NAME))
      .andExpect(header(TOKEN, TOKEN_VALUE))
      .andRespond(withSuccess("""
        {"totalRecords":0,"records":[]}
        """, MediaType.APPLICATION_JSON));

    client.getEntitledApplications(TENANT_NAME, TENANT_NAME, 500, TOKEN_VALUE);

    mockServer.verify();
  }

  @HttpExchange
  interface TenantsQueryTemplateClient {

    @GetExchange("/tenants?query=name=={tenantName}")
    ResultList<Tenant> queryTenantsByName(@PathVariable("tenantName") String tenantName,
      @RequestHeader(TOKEN) String token);
  }

  @HttpExchange
  interface EntitledApplicationsClient {

    @GetExchange("/entitlements/{tenant}/applications")
    ResultList<ApplicationDescriptor> getEntitledApplications(@PathVariable("tenant") String tenant,
      @RequestHeader(TENANT) String tenantHeader,
      @RequestParam("limit") Integer limit,
      @RequestHeader(TOKEN) String token);
  }
}
