package org.folio.okapi.facade.integration.tm;

import static org.folio.common.utils.OkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.okapi.facade.integration.model.ResultList;
import org.folio.okapi.facade.integration.tm.model.Tenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "tenant", url = "${application.mt.url}", configuration = TenantManagerClientConfiguration.class)
public interface TenantManagerClient {
  /**
   * Queries tenant by name from mgr-tenants.
   *
   * @param tenantName - tenant name
   * @param token - optional x-okapi-token header value for authorization in Okapi
   * @return found {@link Tenant} object
   */
  @GetMapping(value = "/tenants?query=name=={tenantName}", consumes = APPLICATION_JSON_VALUE)
  ResultList<Tenant> queryTenantsByName(@PathVariable String tenantName, @RequestHeader(TOKEN) String token);
}
