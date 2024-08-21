package org.folio.okapi.facade.integration.am.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.folio.okapi.facade.domain.dto.InterfaceDescriptor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Module {

  private String id;
  private String name;
  private String version;
  private List<InterfaceDescriptor> provides;
}
