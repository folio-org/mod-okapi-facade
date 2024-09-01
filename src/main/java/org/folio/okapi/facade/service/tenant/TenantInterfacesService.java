package org.folio.okapi.facade.service.tenant;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.folio.okapi.facade.util.PaginationUtil.getWithPagination;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.domain.dto.InterfaceDescriptor;
import org.folio.okapi.facade.integration.ma.MgrApplicationsClient;
import org.folio.okapi.facade.integration.ma.model.ApplicationDescriptor;
import org.folio.okapi.facade.integration.model.ResultList;
import org.folio.okapi.facade.integration.mte.MgrTenantEntitlementsClient;
import org.folio.okapi.facade.integration.mte.model.Entitlement;
import org.folio.okapi.facade.integration.tm.MgrTenantsClient;
import org.folio.okapi.facade.integration.tm.model.Tenant;
import org.folio.okapi.facade.mapper.InterfaceDescriptorMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TenantInterfacesService {

  private final MgrApplicationsClient mgrApplicationsClient;
  private final MgrTenantEntitlementsClient mgrTenantEntitlementsClient;
  private final MgrTenantsClient mgrTenantsClient;
  private final InterfaceDescriptorMapper mapper;

  @Value("${application.mte.querylimit:500}") private int entitlementsQueryLimit = 500;
  @Value("${application.ma.querylimit:500}") private int applicationsQueryLimit = 500;

  public List<InterfaceDescriptor> getTenantInterfaces(String token, String tenantName, boolean full,
    String interfaceType) {
    var tenantId = getTenantId(tenantName, token);

    var entitlements = getAllEntitlements(tenantId, token);
    if (entitlements.isEmpty()) {
      return Collections.emptyList();
    }

    var appDescriptors = getAppDescriptors(entitlements, token);
    var moduleDescriptorsStream =
      appDescriptors.stream().map(ApplicationDescriptor::getModuleDescriptors).filter(Objects::nonNull)
        .flatMap(Collection::stream);
    var interfaceDescriptorsStream =
      moduleDescriptorsStream.map(ModuleDescriptor::getProvides).filter(Objects::nonNull).flatMap(Collection::stream);
    var filteredInterfaceDescriptors =
      interfaceDescriptorsStream.filter(Objects::nonNull).filter(getFilter(interfaceType));

    return map(filteredInterfaceDescriptors, full).toList();
  }

  private UUID getTenantId(String tenantName, String token) {
    var tenants = mgrTenantsClient.queryTenantsByName(tenantName, token);
    if (tenants.getTotalRecords() < 1) {
      throw new EntityNotFoundException("Tenant not found by name " + tenantName);
    }
    return tenants.getRecords().stream().map(Tenant::getId).findFirst().orElseThrow();
  }

  private Stream<InterfaceDescriptor> map(
    Stream<org.folio.common.domain.model.InterfaceDescriptor> interfaceDescriptors, boolean isFull) {
    return interfaceDescriptors.map(!isFull ? mapper::mapSimple : mapper::map);
  }

  private Predicate<? super org.folio.common.domain.model.InterfaceDescriptor> getFilter(String interfaceType) {
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
