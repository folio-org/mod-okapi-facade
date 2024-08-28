package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.common.domain.model.InterfaceDescriptor;
import org.folio.okapi.facade.domain.dto.TenantModuleDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyTenantInterfaceApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyTenantInterfaceController implements ProxyTenantInterfaceApi {

  @Override
  public ResponseEntity<List<InterfaceDescriptor>> getAllInterfaces(String tenantId, Boolean full, String type) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<TenantModuleDescriptor>> getInterface(String interfaceId, String tenantId, String type) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
