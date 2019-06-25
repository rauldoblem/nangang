package com.taiji.emp.nangang.feign;

import com.taiji.emp.res.feign.IHazardRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/hazards")
public interface HazardsClient extends IHazardRestService {
}
