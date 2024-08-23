package org.folio.okapi.facade.integration.tm;

import lombok.Data;
import org.folio.common.configuration.properties.TlsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.mt")
public class MgrTenantsClientProperties {
  private String url;
  private TlsProperties tls;
}
