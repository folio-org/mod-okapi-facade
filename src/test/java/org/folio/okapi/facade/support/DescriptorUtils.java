package org.folio.okapi.facade.support;

import static org.instancio.Instancio.gen;
import static org.instancio.Select.field;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.folio.common.domain.model.LaunchDescriptor;
import org.folio.common.domain.model.ModuleDescriptor;
import org.folio.common.domain.model.RoutingEntry;
import org.folio.okapi.facade.domain.dto.DeploymentDescriptor;
import org.folio.okapi.facade.domain.dto.NodeDescriptor;
import org.folio.okapi.facade.domain.dto.PullDescriptor;
import org.folio.okapi.facade.domain.dto.TenantDescriptor;
import org.folio.okapi.facade.domain.dto.TenantModuleDescriptor;
import org.folio.okapi.facade.domain.dto.TimerDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenDeploymentDescriptor.DeploymentDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenModuleDescriptor.ModuleDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenNodeDescriptor.NodeDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenPullDescriptor.PullDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenRoutingEntry.RoutingEntryProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTenantDescriptor.TenantDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTenantModuleDescriptor.TenantModuleDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTimerDescriptor.TimerDescriptorProvider;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.junit.Given;
import org.instancio.junit.GivenProvider;

@UtilityClass
public class DescriptorUtils {

  private static final Model<LaunchDescriptor> LAUNCH_DESCRIPTOR_MODEL =
    Instancio.of(LaunchDescriptor.class)
      .ignore(field(LaunchDescriptor::getDockerArgs))
      .toModel();

  private static final Model<DeploymentDescriptor> DEPLOYMENT_DESCRIPTOR_MODEL =
    Instancio.of(DeploymentDescriptor.class)
      .setModel(field(DeploymentDescriptor::getDescriptor), LAUNCH_DESCRIPTOR_MODEL)
      .toModel();

  private static final Model<ModuleDescriptor> MODULE_DESCRIPTOR_MODEL =
    Instancio.of(ModuleDescriptor.class)
      .setModel(field(ModuleDescriptor::getLaunchDescriptor), LAUNCH_DESCRIPTOR_MODEL)
      .ignore(field(ModuleDescriptor::getMetadata))
      .ignore(field(ModuleDescriptor::getExtensions))
      .toModel();

  private static final Model<TenantDescriptor> TENANT_DESCRIPTOR_MODEL =
    Instancio.of(TenantDescriptor.class)
      .supply(field(TenantDescriptor::getName),
        random -> random.lowerCaseCharacter() + random.alphanumeric(10).toLowerCase())
      .toModel();

  private static final Model<RoutingEntry> ROUTING_ENTRY_MODEL =
    Instancio.of(RoutingEntry.class).toModel();

  private static final Model<TimerDescriptor> TIMER_DESCRIPTOR_MODEL =
    Instancio.of(TimerDescriptor.class)
      .setModel(field(TimerDescriptor::getRoutingEntry), ROUTING_ENTRY_MODEL)
      .toModel();

  public static DeploymentDescriptor deploymentDescriptor() {
    return Instancio.of(DEPLOYMENT_DESCRIPTOR_MODEL).create();
  }

  public static NodeDescriptor nodeDescriptor() {
    return Instancio.create(NodeDescriptor.class);
  }

  public static PullDescriptor pullDescriptor() {
    return Instancio.create(PullDescriptor.class);
  }

  public static ModuleDescriptor moduleDescriptor() {
    return Instancio.of(MODULE_DESCRIPTOR_MODEL).create();
  }

  public static List<ModuleDescriptor> moduleDescriptors(int maxSize) {
    return moduleDescriptors(1, maxSize);
  }

  public static List<ModuleDescriptor> moduleDescriptors(int minSize, int maxSize) {
    return Instancio.ofList(MODULE_DESCRIPTOR_MODEL)
      .size(gen().ints().range(minSize, maxSize).get())
      .create();
  }

  public static TenantDescriptor tenantDescriptor() {
    return Instancio.of(TENANT_DESCRIPTOR_MODEL).create();
  }

  public static TenantModuleDescriptor tenantModuleDescriptor() {
    return Instancio.create(TenantModuleDescriptor.class);
  }

  public static RoutingEntry routingEntry() {
    return Instancio.of(ROUTING_ENTRY_MODEL).create();
  }

  public static TimerDescriptor timerDescriptor() {
    return Instancio.of(TIMER_DESCRIPTOR_MODEL).create();
  }

  @Given(DeploymentDescriptorProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenDeploymentDescriptor {

    class DeploymentDescriptorProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return deploymentDescriptor();
      }
    }
  }

  @Given(NodeDescriptorProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenNodeDescriptor {

    class NodeDescriptorProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return nodeDescriptor();
      }
    }
  }

  @Given(PullDescriptorProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenPullDescriptor {

    class PullDescriptorProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return pullDescriptor();
      }
    }
  }

  @Given(ModuleDescriptorProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenModuleDescriptor {

    class ModuleDescriptorProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return moduleDescriptor();
      }
    }
  }

  @Given(TenantDescriptorProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenTenantDescriptor {

    class TenantDescriptorProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return tenantDescriptor();
      }
    }
  }

  @Given(TenantModuleDescriptorProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenTenantModuleDescriptor {

    class TenantModuleDescriptorProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return tenantModuleDescriptor();
      }
    }
  }

  @Given(RoutingEntryProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenRoutingEntry {

    class RoutingEntryProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return routingEntry();
      }
    }
  }

  @Given(TimerDescriptorProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenTimerDescriptor {

    class TimerDescriptorProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return timerDescriptor();
      }
    }
  }
}
