package org.folio.okapi.facade;

import org.folio.common.configuration.properties.FolioEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FolioEnvironment.class)
public class OkapiFacadeApplication {

  public static void main(String[] args) {
    SpringApplication.run(OkapiFacadeApplication.class, args);
  }
}
