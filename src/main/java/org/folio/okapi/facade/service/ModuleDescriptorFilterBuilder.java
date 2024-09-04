package org.folio.okapi.facade.service;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.folio.common.utils.CollectionUtils.mapItems;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.folio.common.domain.model.InterfaceDescriptor;
import org.folio.common.domain.model.InterfaceReference;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.utils.ModuleId;

final class ModuleDescriptorFilterBuilder {

  private static final Predicate<ModuleDescriptor> TRUE_PREDICATE = md -> true;

  private String moduleId;
  private String provide;
  private String require;
  private String scope;
  private Boolean preRelease;
  private Boolean npmSnapshot;

  public ModuleDescriptorFilterBuilder withModuleId(String moduleId) {
    this.moduleId = moduleId;
    return this;
  }

  public ModuleDescriptorFilterBuilder withRequired(String require) {
    this.require = require;
    return this;
  }

  public ModuleDescriptorFilterBuilder withProvided(String provide) {
    this.provide = provide;
    return this;
  }

  public ModuleDescriptorFilterBuilder withScope(String scope) {
    this.scope = scope;
    return this;
  }

  public ModuleDescriptorFilterBuilder withPreRelease(String preRelease) {
    this.preRelease = toBoolean(preRelease);
    return this;
  }

  public ModuleDescriptorFilterBuilder withNpmSnapshot(String npmSnapshot) {
    this.npmSnapshot = toBoolean(npmSnapshot);
    return this;
  }

  public Predicate<ModuleDescriptor> build() {
    return moduleIdPredicate(moduleId)
      .and(moduleVersionPredicate(preRelease, npmSnapshot))
      .and(interfacePredicate(ModuleDescriptor::getProvides, provide, scope))
      .and(
        interfacePredicate(md -> toInterfaceDescriptors(md.getRequires()), require, scope)
          .or(interfacePredicate(md -> toInterfaceDescriptors(md.getOptional()), require, scope))
      );
  }

  /**
   * Return boolean value of preRelease/npmSnapshot parameter.
   * Note: copied and adopted from Okapi source code (see org.folio.okapi.util.ModuleVersionFilter)
   *
   * @param param value of preRelease/npmSnapshot parameter in query
   * @return returns false for "false", true for "only", and Optional.empty() for "true" and null/undefined
   * @throws IllegalArgumentException on invalid value
   */
  @SuppressWarnings("java:S2447")
  private static Boolean toBoolean(String param) {
    if (isBlank(param) || "true".equals(param)) {
      return null;
    } else if ("false".equals(param)) {
      return false;
    } else if ("only".equals(param)) {
      return true;
    }
    throw new IllegalArgumentException("Expected \"true\", \"false\", \"only\" or undefined/null "
      + ", but got: " + param);
  }

  private static Predicate<ModuleDescriptor> moduleIdPredicate(String moduleId) {
    return trueIfEmptyOr(md -> {
      var id = new ModuleId(md.getId());
      return id.hasPrefix(new ModuleId(moduleId));
    }).apply(moduleId);
  }

  private static Predicate<ModuleDescriptor> moduleVersionPredicate(Boolean preRelease, Boolean npmSnapshot) {
    return md -> {
      var id = new ModuleId(md.getId());
      if (id.hasPreRelease()) {
        return preRelease == null || preRelease;
      } else if (id.hasNpmSnapshot()) {
        return npmSnapshot == null || npmSnapshot;
      } else {
        return (npmSnapshot == null || !npmSnapshot)
          && (preRelease == null || !preRelease);
      }
    };
  }

  private static Predicate<ModuleDescriptor> interfacePredicate(
    Function<ModuleDescriptor, List<InterfaceDescriptor>> interfaceExtractor, String interfacesStr, String scope) {
    return trueIfEmptyOr(md -> interfaceCheck(interfaceExtractor.apply(md), interfacesStr, scope))
      .apply(interfacesStr);
  }

  /**
   * Check if interface spec matches interface. The version must be exactly the same to match.
   * Note: copied and adopted from Okapi source code (see org.folio.okapi.util.ModuleUtil)
   *
   * @param interfaces interfaces from a module
   * @param interfacesStr interfaces spec consisting of comma separated list of interface spec.
   *                      Each interspace spec is either an interface or an interface followed
   *                      by =, followed by version.
   *
   * @param scope null for all scopes; or scope to match against.
   * @return true if scope and interface versions are the same or if interfaceStr is
   *     null; false otherwise.
   */
  private static boolean interfaceCheck(List<InterfaceDescriptor> interfaces, String interfacesStr, String scope) {
    if (isEmpty(interfaces)) {
      return false;
    }

    var interfaceList = interfacesStr.split(",");
    var interfacePair = new String[interfaceList.length][];
    for (int i = 0; i < interfaceList.length; i++) {
      interfacePair[i] = interfaceList[i].split("=");
    }

    for (var pi : interfaces) {
      var gotScope = emptyIfNull(pi.getScope());

      if (scope == null || gotScope.contains(scope)) {
        for (var kv : interfacePair) {
          if (kv.length == 2) {
            var interfaceUser = new InterfaceDescriptor(kv[0], kv[1]);
            if (interfaceUser.compare(pi) == 0) {
              return true;
            }
          } else if (pi.getId().equals(kv[0])) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private static List<InterfaceDescriptor> toInterfaceDescriptors(List<InterfaceReference> references) {
    return mapItems(references, ref -> new InterfaceDescriptor(ref.getId(), ref.getVersion()));
  }

  private static Function<String, Predicate<ModuleDescriptor>> trueIfEmptyOr(
    Predicate<ModuleDescriptor> nonEmptyParamPredicate) {
    return param -> isBlank(param) ? TRUE_PREDICATE : nonEmptyParamPredicate;
  }
}
