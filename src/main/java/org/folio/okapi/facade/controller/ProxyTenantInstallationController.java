package org.folio.okapi.facade.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.folio.okapi.facade.domain.dto.InstallJob;
import org.folio.okapi.facade.domain.dto.TenantModuleDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyTenantInstallationApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyTenantInstallationController implements ProxyTenantInstallationApi {

  @Override
  public ResponseEntity<List<TenantModuleDescriptor>> createInstallJob(String tenantId,
    List<@Valid TenantModuleDescriptor> tenantModuleDescriptor, String preRelease, String npmSnapshot, Boolean async,
    Boolean deploy, Boolean ignoreErrors, String invoke, Integer parallel, Boolean purge, Boolean reinstall,
    Boolean simulate, String tenantParameters, Boolean depCheck) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<TenantModuleDescriptor>> createUpgradeJob(String tenantId, String preRelease,
    String npmSnapshot, Boolean async, Boolean deploy, Boolean ignoreErrors, String invoke, Integer parallel,
    Boolean purge, Boolean reinstall, Boolean simulate, String tenantParameters, Boolean depCheck) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteInstallJob(String installId, String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteInstallJobs(String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<InstallJob>> getAllInstallJobs(String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<InstallJob> getInstallJob(String installId, String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
