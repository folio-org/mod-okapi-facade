package org.folio.okapi.facade.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.folio.test.types.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class PaginationUtilTest {

  @Test
  void testGetWithPagination() {
    var result = PaginationUtil.getWithPagination(offset -> offset, 2, input -> List.of(input, input), input -> 5);
    assertThat(result).isEqualTo(List.of(0, 0, 2, 2, 4, 4));
  }

  @Test
  void testGetWithPaginationNoData() {
    var result = PaginationUtil.getWithPagination(offset -> offset, 2, input -> List.of(), input -> 1);
    assertThat(result).isEqualTo(List.of());
  }
}
