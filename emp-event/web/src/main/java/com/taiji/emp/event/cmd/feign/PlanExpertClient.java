package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.IPlanExpertRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planExperts")
public interface PlanExpertClient extends IPlanExpertRestService{
}
