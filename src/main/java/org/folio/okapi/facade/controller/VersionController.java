package org.folio.okapi.facade.controller;

import org.folio.okapi.facade.rest.resource.VersionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController implements VersionApi {

  @Override
  public ResponseEntity<Void> getVersion() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
