package org.folio.okapi.facade.integration.ma;

import static org.folio.common.utils.tls.FeignClientTlsUtils.getOkHttpClient;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

public class MgrApplicationsClientConfiguration {

  @Bean
  public OkHttpClient feignClient(okhttp3.OkHttpClient okHttpClient, MgrApplicationsClientProperties properties) {
    return getOkHttpClient(okHttpClient, properties.getTls());
  }
}
