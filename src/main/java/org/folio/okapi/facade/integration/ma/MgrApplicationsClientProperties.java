package org.folio.okapi.facade.integration.ma;

import lombok.Data;
import org.folio.common.configuration.properties.TlsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.ma")
@Data
@Component
public class MgrApplicationsClientProperties {
  private String url;
  private TlsProperties tls;
}
