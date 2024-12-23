package org.folio.okapi.facade.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import org.folio.spring.FolioExecutionContext;
import org.folio.test.types.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class TokenUtilsTest {

  @Mock FolioExecutionContext folioContext;

  @Test
  void extractToken_positive_systemTokenExists() {
    var token = "system-token";
    when(folioContext.getAllHeaders()).thenReturn(Map.of("X-System-Token", List.of(token)));

    var result = TokenUtils.extractToken(folioContext);

    assertThat(result).isEqualTo(token);
    verify(folioContext).getAllHeaders();
    verify(folioContext).getToken();
    verifyNoMoreInteractions(folioContext);
  }

  @Test
  void extractToken_positive_userTokenExists() {
    var token = "user-token";
    when(folioContext.getAllHeaders()).thenReturn(Map.of());
    when(folioContext.getToken()).thenReturn(token);

    var result = TokenUtils.extractToken(folioContext);

    assertThat(result).isEqualTo(token);
    verify(folioContext).getAllHeaders();
    verify(folioContext).getToken();
    verifyNoMoreInteractions(folioContext);
  }

  @Test
  void extractToken_positive_bothTokensExist() {
    var systemToken = "system-token";
    var userToken = "user-token";
    when(folioContext.getAllHeaders()).thenReturn(Map.of("X-System-Token", List.of(systemToken)));
    when(folioContext.getToken()).thenReturn(userToken);

    var result = TokenUtils.extractToken(folioContext);

    assertThat(result).isEqualTo(systemToken);
    verify(folioContext).getAllHeaders();
    verify(folioContext).getToken();
    verifyNoMoreInteractions(folioContext);
  }
}
