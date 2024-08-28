package org.folio.okapi.facade.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.domain.dto.TenantModuleDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyTenantModuleApi;
import org.folio.okapi.facade.service.TenantModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProxyTenantModuleController implements ProxyTenantModuleApi {

  private final TenantModuleService service;

  @Override
  public ResponseEntity<Void> disableTenantModule(String moduleId, String tenantId, String invoke, Boolean purge,
    String tenantParameters) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> disableTenantModules(String tenantId, String invoke, Boolean purge,
    String tenantParameters) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<TenantModuleDescriptor> enableTenantModule(String tenantId,
    TenantModuleDescriptor tenantModuleDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<ModuleDescriptor>> getAllTenantModules(String tenantId, Boolean dot, String filter,
    Boolean full, Integer latest, String order, String orderBy, String provide, String require, String scope,
    String preRelease, String npmSnapshot) {
    return ResponseEntity.ok(
      service.findAll(tenantId, filter, full, latest, order, orderBy, provide, require, scope, preRelease,
        npmSnapshot));
  }

  @Override
  public ResponseEntity<TenantModuleDescriptor> getTenantModule(String moduleId, String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<TenantModuleDescriptor> upgradeTenantModule(String moduleId, String tenantId,
    TenantModuleDescriptor tenantModuleDescriptor, String invoke, Boolean purge, String tenantParameters) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
