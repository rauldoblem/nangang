package com.taiji.emp.nangang.feign;

import com.taiji.emp.res.feign.ITargetRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-res",path = "api/rcTarget")
public interface TargetClient extends ITargetRestService {
}
