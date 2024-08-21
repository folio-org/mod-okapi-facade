package org.folio.okapi.facade.integration.am;

import lombok.Data;
import org.folio.common.configuration.properties.TlsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.am")
@Data
@Component
public class AppManagerProperties {
  private String url;
  private TlsProperties tls;
}
