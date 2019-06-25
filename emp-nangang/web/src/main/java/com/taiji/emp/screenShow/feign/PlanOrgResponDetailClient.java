package com.taiji.emp.screenShow.feign;

import com.taiji.emp.res.feign.IPlanOrgResponDetailRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planOrgResponDetails")
public interface PlanOrgResponDetailClient extends IPlanOrgResponDetailRestService{
}
