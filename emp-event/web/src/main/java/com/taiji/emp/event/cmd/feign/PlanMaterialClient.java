package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.IPlanMaterialRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planMaterials")
public interface PlanMaterialClient extends IPlanMaterialRestService{
}
