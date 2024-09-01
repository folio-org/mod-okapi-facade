package org.folio.okapi.facade.integration.ma;

import static org.folio.common.utils.OkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ResultList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface MgrApplicationsClient {

  @GetMapping(value = "/applications", consumes = APPLICATION_JSON_VALUE)
  ResultList<ApplicationDescriptor> queryApplicationDescriptors(
    @RequestParam("query") String query,
    @RequestParam("full") Boolean full,
    @RequestParam("limit") int limit,
    @RequestParam("offset") int offset,
    @RequestHeader(TOKEN) String token);
}
