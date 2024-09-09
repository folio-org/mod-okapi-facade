package org.folio.okapi.facade.integration.mte;

import static org.folio.spring.integration.XOkapiHeaders.TENANT;
import static org.folio.spring.integration.XOkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ResultList;
import org.folio.okapi.facade.integration.mte.model.Entitlement;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tenantEntitlement",
  url = "${application.mte.url}",
  configuration = MgrTenantEntitlementsClientConfiguration.class)
public interface MgrTenantEntitlementsClient {

  @GetMapping(value = "/entitlements", consumes = APPLICATION_JSON_VALUE)
  ResultList<Entitlement> findByQuery(@RequestParam(value = "query", required = false) String query,
    @RequestParam(value = "limit", required = false) Integer limit,
    @RequestParam(value = "offset", required = false) Integer offset,
    @RequestHeader(TOKEN) String token);

  @GetMapping(value = "/entitlements/{tenant}/applications", consumes = APPLICATION_JSON_VALUE)
  ResultList<ApplicationDescriptor> getEntitledApplications(@PathVariable @RequestHeader(TENANT) String tenant,
    @RequestParam(value = "limit", required = false) Integer limit,
    @RequestHeader(TOKEN) String token);
}
