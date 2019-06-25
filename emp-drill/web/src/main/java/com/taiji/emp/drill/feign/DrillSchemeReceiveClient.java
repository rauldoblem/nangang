package com.taiji.emp.drill.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-drill",path = "api/drillSchemeReceive")
public interface DrillSchemeReceiveClient extends IDrillSchemeReceiveRestService {
}
