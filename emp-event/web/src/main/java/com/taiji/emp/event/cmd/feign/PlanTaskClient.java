package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.IPlanTaskRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planTasks")
public interface PlanTaskClient extends IPlanTaskRestService{
}
