package com.taiji.emp.nangang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/signins/task")
public interface SignTaskClient extends ISignTask {
}
