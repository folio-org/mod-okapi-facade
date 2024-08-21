package org.folio.okapi.facade.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationUtil {
  public static <T, E> List<E> getWithPagination(PageRetriever<T> action, int pageSize,
    Function<T, ? extends Collection<E>> resultsExtractor, Function<T, Integer> totalRecordsCountExtractor) {
    var result = new ArrayList<E>();
    T page;
    var offset = 0;
    Collection<E> pageResults;
    do {
      page = action.getPage(offset);
      offset += pageSize;
      pageResults = resultsExtractor.apply(page);
      result.addAll(pageResults);
    } while (totalRecordsCountExtractor.apply(page) > result.size() && !pageResults.isEmpty());
    return result;
  }

  public interface PageRetriever<T> {
    T getPage(int offset);
  }
}
