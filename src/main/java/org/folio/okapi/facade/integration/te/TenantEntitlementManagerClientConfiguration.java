package org.folio.okapi.facade.integration.te;

import static org.folio.common.utils.FeignClientTlsUtils.getOkHttpClient;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class TenantEntitlementManagerClientConfiguration {

  @Bean
  public OkHttpClient feignClient(okhttp3.OkHttpClient okHttpClient, TenantEntitlementManagerProperties properties) {
    return getOkHttpClient(okHttpClient, properties.getTls());
  }
}
