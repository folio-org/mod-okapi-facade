package org.folio.okapi.facade.integration.mt;

import static org.folio.common.utils.OkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.common.domain.model.ResultList;
import org.folio.okapi.facade.integration.mt.model.Tenant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(contentType = APPLICATION_JSON_VALUE)
public interface MgrTenantsClient {
  /**
   * Queries tenant by name from mgr-tenants.
   *
   * @param tenantName - tenant name
   * @param token - optional x-okapi-token header value for authorization in Okapi
   * @return found {@link Tenant} object
   */
  @GetExchange("/tenants?query=name=={tenantName}")
  ResultList<Tenant> queryTenantsByName(@PathVariable("tenantName") String tenantName,
    @RequestHeader(TOKEN) String token);
}
