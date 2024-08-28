package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.common.domain.model.RoutingEntry;
import org.folio.okapi.facade.domain.dto.TimerDescriptor;
import org.folio.okapi.facade.rest.resource.ProxyTenantTimerApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProxyTenantTimerController implements ProxyTenantTimerApi {

  @Override
  public ResponseEntity<List<TimerDescriptor>> getAllTimers(String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<TimerDescriptor> getTimer(String timerId, String tenantId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> updateTimer(String timerId, String tenantId, RoutingEntry routingEntry) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> updateTimers(String tenantId, TimerDescriptor timerDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
