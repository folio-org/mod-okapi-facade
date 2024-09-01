package org.folio.okapi.facade.service;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.folio.common.utils.CollectionUtils.mapItems;
import static org.folio.common.utils.CollectionUtils.toStream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.integration.mte.MgrTenantEntitlementsService;
import org.folio.okapi.facade.utils.ModuleId;
import org.folio.spring.FolioExecutionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TenantModuleService {

  private final MgrTenantEntitlementsService entitlementService;
  private final FolioExecutionContext folioContext;

  public List<ModuleDescriptor> findAll(String tenantId, String filter, Boolean full, Integer latest,
    String order, String orderBy, String provide, String require, String scope, String preRelease, String npmSnapshot) {
    var token = folioContext.getToken(); // the token is not required by the target endpoint for now
    var apps = entitlementService.getTenantApplications(tenantId, token);

    var mdFilter = createFilter(filter, provide, require, scope, preRelease, npmSnapshot);
    var descriptors = filterModuleDescriptors(apps, mdFilter);

    var latestProducts = getLatestProducts(latest, descriptors);
    var sorted = sortModuleDescriptors(latestProducts, orderBy, order);

    return full
      ? sorted
      : mapItems(sorted, md -> new ModuleDescriptor().id(md.getId()).tags(md.getTags())); // return id and tags only
  }

  private static Predicate<ModuleDescriptor> createFilter(String filter, String provide, String require,
    String scope, String preRelease, String npmSnapshot) {
    return new ModuleDescriptorFilterBuilder()
      .withModuleId(filter)
      .withRequired(require)
      .withProvided(provide)
      .withScope(scope)
      .withPreRelease(preRelease)
      .withNpmSnapshot(npmSnapshot)
      .build();
  }

  private static List<ModuleDescriptor> filterModuleDescriptors(List<ApplicationDescriptor> apps,
    Predicate<ModuleDescriptor> mdFilter) {
    return toStream(apps)
      .flatMap(appDescriptor -> Stream.concat(toStream(appDescriptor.getModuleDescriptors()),
        toStream(appDescriptor.getUiModuleDescriptors())))
      .filter(mdFilter)
      .toList();
  }

  /**
   * Sort descriptors in descending order and remove all but the top-N for each product.
   * Note: copied and adopted from Okapi source code (see org.folio.okapi.util.DepResolution)
   *
   * @param limit max number for each module (Top-N)
   * @param descriptors modules to consider (will be modified!)
   */
  private static List<ModuleDescriptor> getLatestProducts(Integer limit, List<ModuleDescriptor> descriptors) {
    if (limit == null) {
      return descriptors;
    }

    var result = new ArrayList<>(descriptors);

    result.sort(reverseOrder(byModuleId()));

    var it = result.listIterator();
    var product = "";
    int no = 0;
    while (it.hasNext()) {
      var md = it.next();
      var moduleId = new ModuleId(md.getId());

      if (!product.equals(moduleId.getProduct())) {
        product = moduleId.getProduct();
        no = 0;
      } else if (no >= limit) {
        it.remove();
      }
      no++;
    }

    return result;
  }

  private static List<ModuleDescriptor> sortModuleDescriptors(List<ModuleDescriptor> descriptors, String orderBy,
    String order) {
    var result = new ArrayList<>(descriptors);

    if (isBlank(orderBy)) {
      // the default sorting of Module Descriptor in Okapi is based on ModuleId comparison
      result.sort(byModuleId());
      return result;
    }

    if (!"id".equals(orderBy)) {
      throw new IllegalArgumentException("unknown orderBy field: " + orderBy);
    }

    var ord = Order.fromString(defaultIfEmpty(order, Order.DESC.value));
    if (ord == Order.ASC) {
      result.sort(byModuleId());
    } else {
      result.sort(reverseOrder(byModuleId()));
    }

    return result;
  }

  private static Comparator<ModuleDescriptor> byModuleId() {
    return comparing(md -> new ModuleId(md.getId()));
  }

  private enum Order {
    ASC("asc"),
    DESC("desc");

    private final String value;

    Order(String value) {
      this.value = value;
    }

    static Order fromString(String value) {
      for (var b : Order.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("invalid order value: " + value);
    }
  }
}
