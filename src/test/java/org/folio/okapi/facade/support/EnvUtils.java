package org.folio.okapi.facade.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import lombok.experimental.UtilityClass;
import org.folio.okapi.facade.domain.dto.EnvEntry;
import org.folio.okapi.facade.support.EnvUtils.GivenEnvEntry.EnvEntryProvider;
import org.instancio.Instancio;
import org.instancio.junit.Given;
import org.instancio.junit.GivenProvider;

@UtilityClass
public class EnvUtils {

  public static EnvEntry envEntry() {
    return Instancio.create(EnvEntry.class);
  }

  @Given(EnvEntryProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface GivenEnvEntry {

    class EnvEntryProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        return envEntry();
      }
    }
  }
}
