package org.folio.okapi.facade.integration.ma.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class ApplicationDeployment {

  private List<Module> modules;
  private Deployment deployment;

  @JsonProperty("ui-modules")
  private List<Module> uiModules;
}
