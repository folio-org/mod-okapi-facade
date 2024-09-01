package org.folio.okapi.facade.integration.mte;

import static org.folio.common.utils.FeignClientTlsUtils.getOkHttpClient;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class MgrTenantEntitlementsClientConfiguration {

  @Bean
  public OkHttpClient feignClient(okhttp3.OkHttpClient okHttpClient, MgrTenantEntitlementsProperties properties) {
    return getOkHttpClient(okHttpClient, properties.getTls());
  }
}
