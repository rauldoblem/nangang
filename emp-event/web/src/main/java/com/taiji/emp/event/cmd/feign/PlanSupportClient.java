package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.IPlanSupportRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planSupports")
public interface PlanSupportClient extends IPlanSupportRestService{
}
