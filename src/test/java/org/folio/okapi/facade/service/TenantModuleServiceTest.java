package org.folio.okapi.facade.service;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static org.apache.commons.collections4.ListUtils.union;
import static org.assertj.core.api.Assertions.assertThat;
import static org.folio.common.utils.CollectionUtils.toStream;
import static org.folio.test.TestConstants.OKAPI_AUTH_TOKEN;
import static org.folio.test.TestConstants.TENANT_ID;
import static org.folio.test.TestUtils.parse;
import static org.folio.test.TestUtils.readString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.okapi.facade.integration.mte.MgrTenantEntitlementsService;
import org.folio.okapi.facade.utils.ModuleId;
import org.folio.spring.FolioExecutionContext;
import org.folio.test.TestUtils;
import org.folio.test.types.UnitTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class TenantModuleServiceTest {

  @InjectMocks private TenantModuleService service;
  @Mock private MgrTenantEntitlementsService entitlementService;
  @Mock private FolioExecutionContext folioContext;

  private final String tenantId = TENANT_ID;
  private String filter;
  private Boolean full = false;
  private Integer latest;
  private String order;
  private String orderBy;
  private String provide;
  private String require;
  private String scope;
  private String preRelease;
  private String npmSnapshot;

  @BeforeEach
  void setUp() {
    when(folioContext.getToken()).thenReturn(OKAPI_AUTH_TOKEN);
  }

  @AfterEach
  void tearDown() {
    TestUtils.verifyNoMoreInteractions(this);
  }

  @Test
  void findAll_positive_defaultParams() {
    var appDescriptors = testAppDescriptors();
    when(entitlementService.getTenantApplications(tenantId, OKAPI_AUTH_TOKEN)).thenReturn(appDescriptors);

    var found = findAll();

    var expected = extractAllModuleDescriptors(appDescriptors, byModuleId());
    assertThat(found).containsExactlyElementsOf(expected);
  }

  @Test
  void findAll_positive_fullDescriptors() {
    var appDescriptors = testAppDescriptors();
    when(entitlementService.getTenantApplications(tenantId, OKAPI_AUTH_TOKEN)).thenReturn(appDescriptors);

    full = true;
    var found = findAll();

    var expected = extractAllModuleDescriptors(appDescriptors, byModuleId());
    assertThat(found).containsExactlyElementsOf(expected);
  }
  @Test
  void findAll_positive_moduleIdFilter() {
    var appDescriptors = testAppDescriptors();
    when(entitlementService.getTenantApplications(tenantId, OKAPI_AUTH_TOKEN)).thenReturn(appDescriptors);

    filter = "mod-configuration";
    var found = findAll();

    var expected = extractAllModuleDescriptors(appDescriptors, moduleNameIn(filter), byModuleId());
    assertThat(found).containsExactlyElementsOf(expected);
  }

  @Test
  void findAll_positive_providedFilter() {
    var appDescriptors = testAppDescriptors();
    when(entitlementService.getTenantApplications(tenantId, OKAPI_AUTH_TOKEN)).thenReturn(appDescriptors);

    provide = "configuration=2.0,users=16.1";
    var found = findAll();

    var expected = extractAllModuleDescriptors(appDescriptors, moduleNameIn("mod-configuration", "mod-users"),
      byModuleId());
    assertThat(found).containsExactlyElementsOf(expected);
  }

  @Test
  void findAll_positive_requiredFilter() {
    var appDescriptors = testAppDescriptors();
    when(entitlementService.getTenantApplications(tenantId, OKAPI_AUTH_TOKEN)).thenReturn(appDescriptors);

    require = "configuration=2.0,users=16.1";
    var found = findAll();

    var expected = extractAllModuleDescriptors(appDescriptors,
      moduleNameIn("folio_stripes-core", "folio_stripes-smart-components", "mod-login-keycloak", "mod-notes",
        "mod-password-validator", "mod-users-bl", "mod-users-keycloak"),
      byModuleId());
    assertThat(found).containsExactlyElementsOf(expected);
  }

  private List<ModuleDescriptor> findAll() {
    return service.findAll(tenantId, filter, full, latest, order, orderBy, provide, require, scope, preRelease,
      npmSnapshot);
  }

  private List<ModuleDescriptor> extractAllModuleDescriptors(List<ApplicationDescriptor> appDescriptors,
    Comparator<ModuleDescriptor> sorting) {
    return extractAllModuleDescriptors(appDescriptors, descriptor -> true, sorting);
  }

  private List<ModuleDescriptor> extractAllModuleDescriptors(List<ApplicationDescriptor> appDescriptors,
    Predicate<ModuleDescriptor> filtering, Comparator<ModuleDescriptor> sorting) {
    return getModuleDescriptorStream(appDescriptors)
      .filter(filtering)
      .sorted(sorting)
      .map(full ? identity() : idAndTagOnly())
      .toList();
  }

  private static Stream<ModuleDescriptor> getModuleDescriptorStream(List<ApplicationDescriptor> appDescriptors) {
    return extract(toStream(appDescriptors), desc -> union(desc.getModuleDescriptors(), desc.getUiModuleDescriptors()));
  }

  private static List<ApplicationDescriptor> testAppDescriptors() {
    return parse(readString("json/mte/tenant-entitled-applications.json"), new TypeReference<>() {});
  }

  private static Predicate<ModuleDescriptor> moduleNameIn(String... names) {
    return desc -> ArrayUtils.contains(names, desc.getName());
  }

  private static Function<ModuleDescriptor, ModuleDescriptor> idAndTagOnly() {
    return md -> new ModuleDescriptor().id(md.getId()).tags(md.getTags());
  }

  private static Comparator<ModuleDescriptor> byModuleId() {
    return comparing(md -> new ModuleId(md.getId()));
  }

  private static <S, T> Stream<T> extract(Stream<S> stream, Function<S, List<T>> mapper) {
    return stream.map(mapper)
      .filter(Objects::nonNull)
      .flatMap(Collection::stream);
  }
}
