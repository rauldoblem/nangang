package com.taiji.emp.drill.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-drill",path = "api/drillPlan")
public interface DrillPlanClient extends IDrillPlanRestService {
}
