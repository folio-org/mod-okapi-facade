package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.okapi.facade.domain.dto.HealthStatus;
import org.folio.okapi.facade.rest.resource.ProxyHealthApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyHealthController implements ProxyHealthApi {

  @Override
  public ResponseEntity<List<HealthStatus>> getHealthStatus() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
