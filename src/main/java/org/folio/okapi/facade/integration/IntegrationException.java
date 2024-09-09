package org.folio.okapi.facade.integration;

import java.io.Serial;

public class IntegrationException extends RuntimeException {

  @Serial private static final long serialVersionUID = -4271109000774923929L;

  public IntegrationException(String message, Throwable cause) {
    super(message, cause);
  }
}
