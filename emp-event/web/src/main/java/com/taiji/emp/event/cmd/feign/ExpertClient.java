package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.IExpertRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/experts")
public interface ExpertClient extends IExpertRestService{
}
