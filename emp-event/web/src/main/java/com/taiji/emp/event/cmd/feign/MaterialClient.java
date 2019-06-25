package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.IMaterialRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/materials")
public interface MaterialClient extends IMaterialRestService{
}
