package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.okapi.facade.domain.dto.EnvEntry;
import org.folio.okapi.facade.rest.resource.EnvironmentApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentController implements EnvironmentApi {

  @Override
  public ResponseEntity<EnvEntry> createEnvironmentEntry(EnvEntry envEntry) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteEnvironmentEntry(String id) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<EnvEntry>> getAllEnvironmentEntries() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<EnvEntry> getEnvironmentEntry(String id) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
