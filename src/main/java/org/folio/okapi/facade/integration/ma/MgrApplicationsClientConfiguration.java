package org.folio.okapi.facade.integration.ma;

import org.folio.common.utils.tls.HttpClientTlsUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MgrApplicationsClientConfiguration {

  @Bean
  public MgrApplicationsClient mgrApplicationsClient(MgrApplicationsClientProperties properties) {
    return HttpClientTlsUtils.buildHttpServiceClient(
      RestClient.builder(), properties.getTls(), properties.getUrl(), MgrApplicationsClient.class);
  }
}
