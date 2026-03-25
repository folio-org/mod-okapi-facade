package org.folio.okapi.facade.integration.mte;

import static org.folio.spring.integration.XOkapiHeaders.TENANT;
import static org.folio.spring.integration.XOkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ResultList;
import org.folio.okapi.facade.integration.mte.model.Entitlement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(contentType = APPLICATION_JSON_VALUE)
public interface MgrTenantEntitlementsClient {

  default ResultList<ApplicationDescriptor> getEntitledApplications(String tenant, Integer limit, String token) {
    return getEntitledApplications(tenant, tenant, limit, token);
  }

  @GetExchange("/entitlements/{tenant}/applications")
  ResultList<ApplicationDescriptor> getEntitledApplications(@PathVariable("tenant") String tenant,
    @RequestHeader(TENANT) String tenantHeader,
    @RequestParam(value = "limit", required = false) Integer limit,
    @RequestHeader(TOKEN) String token);

  @GetExchange("/entitlements")
  ResultList<Entitlement> findByQuery(@RequestParam(value = "query", required = false) String query,
    @RequestParam(value = "limit", required = false) Integer limit,
    @RequestParam(value = "offset", required = false) Integer offset,
    @RequestHeader(TOKEN) String token);
}
