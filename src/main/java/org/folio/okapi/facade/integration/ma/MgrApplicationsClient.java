package org.folio.okapi.facade.integration.ma;

import static org.folio.common.utils.OkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ResultList;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(contentType = APPLICATION_JSON_VALUE)
public interface MgrApplicationsClient {

  @GetExchange("/applications")
  ResultList<ApplicationDescriptor> queryApplicationDescriptors(
    @RequestParam("query") String query,
    @RequestParam("full") Boolean full,
    @RequestParam("limit") int limit,
    @RequestParam("offset") int offset,
    @RequestHeader(TOKEN) String token);
}
