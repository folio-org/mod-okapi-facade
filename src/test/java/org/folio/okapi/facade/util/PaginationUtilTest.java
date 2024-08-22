package org.folio.okapi.facade.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class PaginationUtilTest {

  @Test
  public void testGetWithPagination() {
    var result = PaginationUtil.getWithPagination(offset -> offset, 2, input -> List.of(input, input), input -> 5);
    assertThat(result).isEqualTo(List.of(0, 0, 2, 2, 4, 4));
  }
}
