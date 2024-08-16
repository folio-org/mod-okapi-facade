package org.folio.okapi.facade.controller;

import static org.folio.test.TestUtils.asJsonString;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;
import org.folio.okapi.facade.domain.dto.ModuleDescriptor;
import org.folio.okapi.facade.support.DescriptorUtils.GivenModuleDescriptor;
import org.folio.okapi.facade.support.ValueUtils.GivenInteger;
import org.folio.test.types.UnitTest;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@UnitTest
@WebMvcTest(ProxyModuleController.class)
@ExtendWith(InstancioExtension.class)
class ProxyModuleControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void announceModule_negative_notImplemented(@GivenModuleDescriptor ModuleDescriptor descriptor) throws Exception {
    mockMvc.perform(post("/_/proxy/modules")
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptor)))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void announceModules_negative_notImplemented(@GivenModuleDescriptor Stream<ModuleDescriptor> descriptors)
    throws Exception {
    mockMvc.perform(post("/_/proxy/import/modules")
        .header(ACCEPT, APPLICATION_JSON_VALUE)
        .contentType(APPLICATION_JSON)
        .content(asJsonString(descriptors.limit(10).toList())))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void cleanupModules_negative_notImplemented(
    @GivenInteger(min = 0, max = 100) Integer saveReleases,
    @GivenInteger(min = 0, max = 100) Integer saveSnapshots) throws Exception {
    mockMvc.perform(post("/_/proxy/cleanup/modules")
        .queryParam("saveReleases", saveReleases.toString())
        .queryParam("saveSnapshots", saveSnapshots.toString()))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void deleteModule_negative_notImplemented(@Given String moduleId) throws Exception {
    mockMvc.perform(delete("/_/proxy/modules/{moduleId}", moduleId))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getAllModules_negative_notImplemented() throws Exception {
    mockMvc.perform(get("/_/proxy/modules")
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }

  @Test
  void getModule_negative_notImplemented(@Given String moduleId) throws Exception {
    mockMvc.perform(get("/_/proxy/modules/{moduleId}", moduleId)
        .header(ACCEPT, APPLICATION_JSON_VALUE))
      .andExpect(status().isNotImplemented());
  }
}
