package org.folio.okapi.facade.mapper;

import org.folio.okapi.facade.domain.dto.InterfaceDescriptor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class InterfaceDescriptorMapper {

  public abstract InterfaceDescriptor map(org.folio.common.domain.model.InterfaceDescriptor interfaceDescriptor);

  public InterfaceDescriptor mapSimple(org.folio.common.domain.model.InterfaceDescriptor interfaceDescriptor) {
    if (interfaceDescriptor == null) {
      return null;
    }
    var result = new InterfaceDescriptor();
    result.setId(interfaceDescriptor.getId());
    result.setVersion(interfaceDescriptor.getVersion());
    return result;
  }
}
