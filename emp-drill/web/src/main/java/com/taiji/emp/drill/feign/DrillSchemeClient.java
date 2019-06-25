package com.taiji.emp.drill.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;

@FeignClient(value = "base-server-zuul/micro-emp-drill",path = "api/drillScheme")
public interface DrillSchemeClient extends IDrillSchemeRestService {

}
