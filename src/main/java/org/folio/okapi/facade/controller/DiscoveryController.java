package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.okapi.facade.domain.dto.DeploymentDescriptor;
import org.folio.okapi.facade.domain.dto.HealthDescriptor;
import org.folio.okapi.facade.domain.dto.NodeDescriptor;
import org.folio.okapi.facade.rest.resource.DiscoveryApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscoveryController implements DiscoveryApi {

  @Override
  public ResponseEntity<DeploymentDescriptor> createDiscovery(DeploymentDescriptor deploymentDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteAllDiscoveries() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteDiscoveriesByService(String serviceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<Void> deleteDiscoveryByInstance(String instanceId, String serviceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<DeploymentDescriptor>> getAllDiscoveries() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<HealthDescriptor>> getAllDiscoveryHealth() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<NodeDescriptor>> getAllDiscoveryNodes() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<DeploymentDescriptor>> getDiscoveriesByService(String serviceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<DeploymentDescriptor> getDiscoveryByInstance(String instanceId, String serviceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<HealthDescriptor> getDiscoveryHealthByInstance(String instanceId, String serviceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<HealthDescriptor>> getDiscoveryHealthByService(String serviceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<NodeDescriptor> getDiscoveryNode(String nodeId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<NodeDescriptor> updateDiscoveryNode(String nodeId, NodeDescriptor nodeDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
