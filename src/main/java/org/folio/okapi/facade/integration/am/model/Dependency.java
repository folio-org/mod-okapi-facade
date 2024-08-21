package org.folio.okapi.facade.integration.am.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Dependency {

  private String name;
  private String version;
}
