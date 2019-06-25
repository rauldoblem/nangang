package com.taiji.emp.event.eva.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/process")
public interface EventEvaProcessClient extends IEventEvaProcessRestService {
}
