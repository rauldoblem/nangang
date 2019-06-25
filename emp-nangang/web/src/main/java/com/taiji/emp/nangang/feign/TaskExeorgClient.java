package com.taiji.emp.nangang.feign;

import com.taiji.emp.event.cmd.feign.ITaskExeorgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/task/exeorg")
public interface TaskExeorgClient extends ITaskExeorgRestService {
}
