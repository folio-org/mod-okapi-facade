package org.folio.okapi.facade.integration.mt;

import org.folio.common.utils.tls.HttpClientTlsUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MgrTenantsClientConfiguration {

  @Bean
  public MgrTenantsClient mgrTenantsClient(MgrTenantsClientProperties properties) {
    return HttpClientTlsUtils.buildHttpServiceClient(
      RestClient.builder(), properties.getTls(), properties.getUrl(), MgrTenantsClient.class);
  }
}
