package org.folio.okapi.facade.integration.mte;

import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.Data;
import okhttp3.OkHttpClient;
import org.folio.common.configuration.properties.TlsProperties;
import org.folio.common.utils.FeignClientTlsUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Data
@Configuration
@Import(FeignClientsConfiguration.class)
@ConfigurationProperties(prefix = "tenant.entitlement")
public class MgrTenantEntitlementsClientConfiguration {

  private String url;

  private TlsProperties tls;

  @Bean
  public MgrTenantEntitlementsClient tenantEntitlementClient(OkHttpClient okHttpClient, Contract contract,
    Encoder encoder,
    Decoder decoder) {
    return FeignClientTlsUtils.buildTargetFeignClient(okHttpClient, contract, encoder, decoder, tls, url,
      MgrTenantEntitlementsClient.class);
  }
}
