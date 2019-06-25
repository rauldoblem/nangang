package com.taiji.emp.res.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planSupports")
public interface PlanSupportClient extends IPlanSupportRestService{
}
