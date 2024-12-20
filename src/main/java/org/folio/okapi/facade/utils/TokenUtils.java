package org.folio.okapi.facade.utils;

import static org.folio.common.utils.CollectionUtils.toStream;

import lombok.experimental.UtilityClass;
import org.folio.spring.FolioExecutionContext;

@UtilityClass
public class TokenUtils {

  public static final String SYSTEM_TOKEN = "X-System-Token";

  public static String extractToken(FolioExecutionContext folioContext) {
    return toStream(folioContext.getAllHeaders().get(SYSTEM_TOKEN))
      .findFirst()
      .orElse(folioContext.getToken());
  }
}
