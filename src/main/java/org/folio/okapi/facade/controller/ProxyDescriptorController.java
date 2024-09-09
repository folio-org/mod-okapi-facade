package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.domain.dto.PullDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyDescriptorApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyDescriptorController implements ProxyDescriptorApi {

  @Override
  public ResponseEntity<List<ModuleDescriptor>> pullDescriptors(PullDescriptor pullDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
