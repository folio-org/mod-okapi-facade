package org.folio.okapi.facade.controller;

import org.folio.okapi.facade.rest.resource.InvokeServiceApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvokeServiceController implements InvokeServiceApi {

  @Override
  public ResponseEntity<Void> invoke(String id) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
