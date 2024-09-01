package org.folio.okapi.facade.integration.mte;

import static org.folio.common.utils.OkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.okapi.facade.integration.model.ResultList;
import org.folio.okapi.facade.integration.mte.model.Entitlement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface MgrTenantEntitlementsClient {

  @GetMapping(value = "/entitlements", consumes = APPLICATION_JSON_VALUE)
  ResultList<Entitlement> findByQuery(@RequestParam(value = "query", required = false) String query,
    @RequestParam(value = "limit", required = false) Integer limit,
    @RequestParam(value = "offset", required = false) Integer offset,
                                      @RequestHeader(TOKEN) String token);
}
