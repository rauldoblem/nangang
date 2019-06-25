package com.taiji.emp.event.cmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/task")
public interface CmdTaskClient extends ITrackRestService{
}
