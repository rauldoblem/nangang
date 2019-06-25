package com.taiji.emp.nangang.feign;

import com.taiji.emp.event.eva.feign.IEventEvaProcessRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/process")
public interface EventEvaProcessClient extends IEventEvaProcessRestService {
}
