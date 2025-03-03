package org.folio.okapi.facade.utils;

import static org.folio.common.utils.CollectionUtils.toStream;

import lombok.experimental.UtilityClass;
import org.folio.spring.FolioExecutionContext;

@UtilityClass
public class TokenUtils {

  public static final String SYSTEM_TOKEN = "x-system-token";

  /**
   * Extracts the system token from the FolioExecutionContext. If the system token is not present, the user token is
   * returned.
   *
   * @param folioContext - the FolioExecutionContext
   * @return - the system token or the user token
   */
  public static String extractToken(FolioExecutionContext folioContext) {
    return toStream(folioContext.getAllHeaders().get(SYSTEM_TOKEN))
      .findFirst()
      .orElse(folioContext.getToken());
  }
}
