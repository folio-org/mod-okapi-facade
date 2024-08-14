package org.folio.okapi.facade.support;

import static org.instancio.Select.field;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import lombok.experimental.UtilityClass;
import org.folio.okapi.facade.domain.dto.DeploymentDescriptor;
import org.folio.okapi.facade.domain.dto.LaunchDescriptor;
import org.folio.okapi.facade.domain.dto.ModuleDescriptor;
import org.folio.okapi.facade.domain.dto.NodeDescriptor;
import org.folio.okapi.facade.domain.dto.PullDescriptor;
import org.folio.okapi.facade.domain.dto.TenantDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenDeploymentDescriptor.DeploymentDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenModuleDescriptor.ModuleDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenNodeDescriptor.NodeDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenPullDescriptor.PullDescriptorProvider;
import org.folio.okapi.facade.support.DescriptorUtils.GivenTenantDescriptor.TenantDescriptorProvider;
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
      .toModel();

  private static final Model<TenantDescriptor> TENANT_DESCRIPTOR_MODEL =
    Instancio.of(TenantDescriptor.class)
      .supply(field(TenantDescriptor::getName),
        random -> random.lowerCaseCharacter() + random.alphanumeric(10).toLowerCase())
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

  public static TenantDescriptor tenantDescriptor() {
    return Instancio.of(TENANT_DESCRIPTOR_MODEL).create();
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
}
