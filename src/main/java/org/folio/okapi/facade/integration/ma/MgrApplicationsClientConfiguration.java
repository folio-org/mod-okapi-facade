package org.folio.okapi.facade.integration.ma;

import static org.folio.common.utils.FeignClientTlsUtils.buildTargetFeignClient;

import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import okhttp3.OkHttpClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FeignClientsConfiguration.class)
public class MgrApplicationsClientConfiguration {

  @Bean
  public MgrApplicationsClient applicationManagerClient(OkHttpClient okHttpClient, Contract contract,
    Encoder encoder, Decoder decoder, MgrApplicationsClientProperties config) {
    return buildTargetFeignClient(okHttpClient, contract, encoder, decoder, config.getTls(), config.getUrl(),
      MgrApplicationsClient.class);
  }
}
