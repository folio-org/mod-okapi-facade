package org.folio.okapi.facade.integration.mt.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Tenant {

  private UUID id;
  private String name;
  private String description;
}
