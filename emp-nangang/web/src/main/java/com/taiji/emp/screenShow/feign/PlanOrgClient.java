package com.taiji.emp.screenShow.feign;

import com.taiji.emp.res.feign.IPlanOrgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planOrgs")
public interface PlanOrgClient extends IPlanOrgRestService{
}
