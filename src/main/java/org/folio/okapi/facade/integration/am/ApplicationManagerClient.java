package org.folio.okapi.facade.integration.am;

import static org.folio.common.utils.OkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.okapi.facade.integration.am.model.ApplicationDescriptor;
import org.folio.okapi.facade.integration.am.model.ModuleDiscovery;
import org.folio.okapi.facade.integration.model.ResultList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface ApplicationManagerClient {

  /**
   * Retrieves {@link ApplicationDescriptor} value by id from mgr-applications.
   *
   * @param applicationId - application descriptor identifier
   * @param full - defines if application descriptor must be loaded with all module descriptors
   * @param token - okapi auth token
   * @return retrieved {@link ApplicationDescriptor} object
   */
  @GetMapping(value = "/applications/{id}", consumes = APPLICATION_JSON_VALUE)
  ApplicationDescriptor getApplicationDescriptor(
    @PathVariable("id") String applicationId,
    @RequestParam("full") Boolean full,
    @RequestHeader(TOKEN) String token);

  @GetMapping(value = "/applications", consumes = APPLICATION_JSON_VALUE)
  ResultList<ApplicationDescriptor> queryApplicationDescriptors(
    @RequestParam("query") String query,
    @RequestParam("full") Boolean full,
    @RequestParam("limit") int limit,
    @RequestParam("offset") int offset,
    @RequestHeader(TOKEN) String token);

  @PostMapping(value = "/applications/validate", consumes = APPLICATION_JSON_VALUE)
  void validate(@RequestBody ApplicationDescriptor descriptor, @RequestHeader(TOKEN) String token);

  @GetMapping(value = "/applications/{id}/discovery", consumes = APPLICATION_JSON_VALUE)
  ResultList<ModuleDiscovery> getModuleDiscoveries(
    @PathVariable("id") String applicationId,
    @RequestParam("limit") int limit,
    @RequestHeader(TOKEN) String token);
}
