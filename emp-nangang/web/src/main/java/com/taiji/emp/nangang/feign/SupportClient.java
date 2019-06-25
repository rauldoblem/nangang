package com.taiji.emp.nangang.feign;

import com.taiji.emp.res.feign.ISupportRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/supports")
public interface SupportClient extends ISupportRestService {
}
