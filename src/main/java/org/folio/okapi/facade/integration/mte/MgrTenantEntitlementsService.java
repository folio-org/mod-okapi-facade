package org.folio.okapi.facade.integration.mte;

import feign.FeignException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.common.domain.model.ApplicationDescriptor;
import org.folio.okapi.facade.integration.IntegrationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MgrTenantEntitlementsService {

  @Value("${application.mte.query-limit}")
  private int queryLimit = 500;
  private final MgrTenantEntitlementsClient client;

  @Cacheable(cacheNames = "tenant-applications", key = "#tenantName")
  public List<ApplicationDescriptor> getTenantApplications(String tenantName, String authToken) {
    log.info("Querying tenant applications: tenantName = {}", tenantName);
    try {
      var apps = client.getEntitledApplications(tenantName, queryLimit, authToken);
      return apps.getRecords();
    } catch (FeignException.NotFound | FeignException.BadRequest e) {
      throw e;
    } catch (FeignException cause) {
      throw new IntegrationException("Failed to query tenant applications by tenant name: " + tenantName, cause);
    }
  }
}
