package org.folio.okapi.facade.controller;

import static org.folio.okapi.facade.utils.TokenUtils.extractToken;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.folio.common.domain.model.InterfaceDescriptor;
import org.folio.okapi.facade.domain.dto.TenantModuleDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyTenantInterfaceApi;
import org.folio.okapi.facade.service.TenantInterfacesService;
import org.folio.spring.FolioExecutionContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProxyTenantInterfaceController implements ProxyTenantInterfaceApi {

  private final TenantInterfacesService tenantInterfacesService;
  private final FolioExecutionContext folioExecutionContext;

  @Override
  public ResponseEntity<List<InterfaceDescriptor>> getAllInterfaces(String tenantId, Boolean full, String type) {
    return ResponseEntity.ofNullable(
      tenantInterfacesService.getTenantInterfaces(extractToken(folioExecutionContext), tenantId, full != null && full,
        type));
  }

  @Override
  public ResponseEntity<List<TenantModuleDescriptor>> getInterface(String interfaceId, String tenantId, String type) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
