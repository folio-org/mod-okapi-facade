package org.folio.okapi.facade.integration.mte;

import lombok.Data;
import org.folio.common.configuration.properties.TlsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.mte")
public class MgrTenantEntitlementsProperties {

  private String url;

  /**
   * Properties object with an information about TLS connection.
   */
  private TlsProperties tls;
}
