package org.folio.okapi.facade.service;

import static org.folio.common.utils.CollectionUtils.toStream;

import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.okapi.facade.domain.dto.ModuleDescriptor;
import org.folio.okapi.facade.integration.te.TenantEntitlementManagerService;
import org.folio.spring.FolioExecutionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TenantModuleService {

  private final TenantEntitlementManagerService entitlementService;
  private final FolioExecutionContext folioContext;

  public List<ModuleDescriptor> findAll(String tenantId, String filter, Boolean full, Integer latest,
    String order, String orderBy, String provide, String require, String scope, String preRelease, String npmSnapshot) {
    var token = folioContext.getToken(); // the token is not required by target endpoint for now
    var apps = entitlementService.getTenantApplications(tenantId, token);

    var descriptors = apps.stream()
      .flatMap(appDescriptor -> Stream.concat(toStream(appDescriptor.getModuleDescriptors()),
        toStream(appDescriptor.getUiModuleDescriptors())))
      .toList();

    return descriptors;
  }
}
