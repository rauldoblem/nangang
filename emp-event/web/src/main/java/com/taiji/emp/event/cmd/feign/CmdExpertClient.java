package com.taiji.emp.event.cmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/experts")
public interface CmdExpertClient extends ICmdExpertRestService{
}
