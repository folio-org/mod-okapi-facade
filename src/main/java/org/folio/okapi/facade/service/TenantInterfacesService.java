package org.folio.okapi.facade.service;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.folio.common.utils.CollectionUtils.toStream;
import static org.folio.okapi.facade.util.PaginationUtil.getWithPagination;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.InterfaceDescriptor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.common.domain.model.ResultList;
import org.folio.okapi.facade.integration.ma.MgrApplicationsClient;
import org.folio.okapi.facade.integration.mt.MgrTenantsClient;
import org.folio.okapi.facade.integration.mt.model.Tenant;
import org.folio.okapi.facade.integration.mte.MgrTenantEntitlementsClient;
import org.folio.okapi.facade.integration.mte.model.Entitlement;
import org.folio.spring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TenantInterfacesService {

  private final MgrApplicationsClient mgrApplicationsClient;
  private final MgrTenantEntitlementsClient mgrTenantEntitlementsClient;
  private final MgrTenantsClient mgrTenantsClient;

  @Value("${application.mte.query-limit:500}") private int entitlementsQueryLimit = 500;
  @Value("${application.ma.query-limit:500}") private int applicationsQueryLimit = 500;

  public List<InterfaceDescriptor> getTenantInterfaces(String token, String tenantName, boolean full,
    String interfaceType) {
    var tenantId = getTenantId(tenantName, token);

    var entitlements = getAllEntitlements(tenantId, token);
    if (entitlements.isEmpty()) {
      return Collections.emptyList();
    }

    var appDescriptors = getAppDescriptors(entitlements, token);

    var moduleDescriptors = extract(toStream(appDescriptors), ApplicationDescriptor::getModuleDescriptors);
    var providedInterfaces = extract(moduleDescriptors, ModuleDescriptor::getProvides);

    return providedInterfaces.filter(Objects::nonNull)
      .filter(getFilter(interfaceType))
      .map(desc -> full ? desc : new InterfaceDescriptor(desc.getId(), desc.getVersion()))
      .toList();
  }

  private static <S, T> Stream<T> extract(Stream<S> stream, Function<S, List<T>> mapper) {
    return stream.map(mapper)
      .filter(Objects::nonNull)
      .flatMap(Collection::stream);
  }

  private UUID getTenantId(String tenantName, String token) {
    var tenants = mgrTenantsClient.queryTenantsByName(tenantName, token);
    if (tenants.getTotalRecords() < 1) {
      throw new NotFoundException("Tenant not found by name " + tenantName);
    }
    return tenants.getRecords().stream().map(Tenant::getId).findFirst().orElseThrow();
  }

  private Predicate<? super InterfaceDescriptor> getFilter(String interfaceType) {
    if (isBlank(interfaceType)) {
      return data -> true;
    }
    return data -> (isBlank(data.getInterfaceType()) ? "proxy" : data.getInterfaceType()).equalsIgnoreCase(
      interfaceType);
  }

  protected List<Entitlement> getAllEntitlements(UUID tenantId, String token) {
    var query = String.format("tenantId==%s", tenantId.toString());

    return getWithPagination(
      offset -> mgrTenantEntitlementsClient.findByQuery(query, entitlementsQueryLimit, offset, token),
      entitlementsQueryLimit, ResultList::getRecords, ResultList::getTotalRecords);
  }

  protected List<ApplicationDescriptor> getAppDescriptors(List<Entitlement> entitlements, String token) {
    var applications =
      entitlements.stream().map(Entitlement::getApplicationId).collect(toCollection(TreeSet::new));
    var applicationsQuery =
      applications.stream().map(appIdAndVer -> "id==" + appIdAndVer).collect(joining(" OR "));

    return getWithPagination(
      offset -> mgrApplicationsClient.queryApplicationDescriptors(applicationsQuery, true, applicationsQueryLimit,
        offset, token), applicationsQueryLimit, ResultList::getRecords, ResultList::getTotalRecords);
  }
}
