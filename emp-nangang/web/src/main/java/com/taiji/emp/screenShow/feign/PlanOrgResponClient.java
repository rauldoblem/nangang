package com.taiji.emp.screenShow.feign;

import com.taiji.emp.res.feign.IPlanOrgResponRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planOrgRespons")
public interface PlanOrgResponClient extends IPlanOrgResponRestService{
}
