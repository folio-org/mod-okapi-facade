package org.folio.okapi.facade.integration.tm;

import static org.folio.common.utils.FeignClientTlsUtils.buildTargetFeignClient;

import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FeignClientsConfiguration.class)
public class MgrTenantsClientConfiguration {

  @Bean
  public MgrTenantsClient tenantManagerClient(okhttp3.OkHttpClient okHttpClient, Contract contract,
    Encoder encoder, Decoder decoder, MgrTenantsClientProperties config) {
    return buildTargetFeignClient(okHttpClient, contract, encoder, decoder, config.getTls(), config.getUrl(),
      MgrTenantsClient.class);
  }
}