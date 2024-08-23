package org.folio.okapi.facade.integration.te;

import static org.folio.spring.integration.XOkapiHeaders.TENANT;
import static org.folio.spring.integration.XOkapiHeaders.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.common.domain.model.ResultList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "tenantEntitlement",
  url = "${application.mte.url}",
  configuration = TenantEntitlementManagerClientConfiguration.class)
public interface TenantEntitlementManagerClient {

  @GetMapping(value = "/entitlements/{tenant}/applications", consumes = APPLICATION_JSON_VALUE)
  ResultList<ApplicationDescriptor> getEntitledApplications(@PathVariable @RequestHeader(TENANT) String tenant,
    @RequestHeader(TOKEN) String token);
}
