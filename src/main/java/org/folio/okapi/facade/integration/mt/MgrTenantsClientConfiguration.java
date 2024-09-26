package org.folio.okapi.facade.integration.mt;

import static org.folio.common.utils.tls.FeignClientTlsUtils.getOkHttpClient;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class MgrTenantsClientConfiguration {

  @Bean
  public OkHttpClient tenantManagerClient(okhttp3.OkHttpClient okHttpClient, MgrTenantsClientProperties properties) {
    return getOkHttpClient(okHttpClient, properties.getTls());
  }
}
