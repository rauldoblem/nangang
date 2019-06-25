package com.taiji.emp.event.eva.feign;

import com.taiji.emp.event.redis.feign.IProcessNodeRedisRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/process/node")
public interface EventEvaProcessNodeClient extends IProcessNodeRedisRestService {
}
