package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.okapi.facade.domain.dto.ModuleDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyModuleApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyModuleController implements ProxyModuleApi {

  @Override
  public ResponseEntity<ModuleDescriptor> announceModule(ModuleDescriptor moduleDescriptor, Boolean check,
    String preRelease, String npmSnapshot) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> announceModules(List<ModuleDescriptor> moduleDescriptor, Boolean check,
    String preRelease, String npmSnapshot) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> cleanupModules(Integer saveReleases, Integer saveSnapshots, Boolean removeDependencies) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteModule(String moduleId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<ModuleDescriptor>> getAllModules(Boolean dot, String filter, Boolean full, Integer latest,
    String order, String orderBy, String provide, String require, String scope, String preRelease, String npmSnapshot) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<ModuleDescriptor> getModule(String moduleId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
