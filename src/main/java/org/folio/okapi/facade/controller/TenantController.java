package org.folio.okapi.facade.controller;

import static org.springframework.http.ResponseEntity.noContent;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.folio.tenant.domain.dto.TenantAttributes;
import org.folio.tenant.rest.resource.TenantApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController("folioTenantController")
public class TenantController implements TenantApi {

  @Override
  public ResponseEntity<Void> deleteTenant(String operationId) {
    log.info("Deleting tenant: operationId = {}", operationId);
    return noContent().build();
  }

  @Override
  public ResponseEntity<String> getTenant(String operationId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> postTenant(@Valid TenantAttributes tenantAttributes) {
    log.info("Posting tenant: tenantAttributes = {}", tenantAttributes);
    return noContent().build();
  }
}
