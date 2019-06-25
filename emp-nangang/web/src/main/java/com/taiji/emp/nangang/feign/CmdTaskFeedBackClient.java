package com.taiji.emp.nangang.feign;

import com.taiji.emp.event.cmd.feign.ITaskFeedBackRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/task/feed/back")
public interface CmdTaskFeedBackClient extends ITaskFeedBackRestService {
}
