package org.folio.okapi.facade.controller;

import java.util.List;
import org.folio.okapi.facade.domain.dto.DeploymentDescriptor;
import org.folio.okapi.facade.rest.resource.DeploymentApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeploymentController implements DeploymentApi {

  @Override
  public ResponseEntity<Void> deleteDeployedModuleInstance(String instanceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<DeploymentDescriptor> deployModuleInstance(DeploymentDescriptor deploymentDescriptor) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<List<DeploymentDescriptor>> getAllDeployedModuleInstances() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public ResponseEntity<DeploymentDescriptor> getDeployedModuleInstance(String instanceId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
