package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.okapi.facade.domain.dto.TenantDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyTenantApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyTenantController implements ProxyTenantApi {

  @Override
  public ResponseEntity<TenantDescriptor> createTenant(TenantDescriptor tenantDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteTenant(String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<TenantDescriptor>> getAllTenants() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<TenantDescriptor> getTenant(String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<TenantDescriptor> updateTenant(String tenantId, TenantDescriptor tenantDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
