package org.folio.okapi.facade.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.experimental.UtilityClass;
import org.folio.okapi.facade.support.ValueUtils.GivenInteger.IntegerProvider;
import org.instancio.junit.Given;
import org.instancio.junit.GivenProvider;

@UtilityClass
public class ValueUtils {

  @Given(IntegerProvider.class)
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD, ElementType.PARAMETER})
  public @interface GivenInteger {

    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;

    class IntegerProvider implements GivenProvider {
      @Override
      public Object provide(ElementContext context) {
        GivenInteger givenInteger = context.getAnnotation(GivenInteger.class);
        return context.random().intRange(givenInteger.min(), givenInteger.max());
      }
    }
  }
}
