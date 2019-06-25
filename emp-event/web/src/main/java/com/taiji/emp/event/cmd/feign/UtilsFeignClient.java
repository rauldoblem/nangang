package com.taiji.emp.event.cmd.feign;

import com.taiji.micro.common.feign.IUtilsRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/utils")
public interface UtilsFeignClient extends IUtilsRestService {
}
