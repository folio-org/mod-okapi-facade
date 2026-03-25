package org.folio.okapi.facade.integration.mte;

import org.folio.common.utils.tls.HttpClientTlsUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MgrTenantEntitlementsClientConfiguration {

  @Bean
  public MgrTenantEntitlementsClient mgrTenantEntitlementsClient(MgrTenantEntitlementsProperties properties) {
    return HttpClientTlsUtils.buildHttpServiceClient(
      RestClient.builder(), properties.getTls(), properties.getUrl(), MgrTenantEntitlementsClient.class);
  }
}
