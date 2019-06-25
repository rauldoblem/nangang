package com.taiji.emp.screenShow.feign;

import com.taiji.emp.event.cmd.feign.ICmdTeamRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/teams")
public interface CmdTeamClient extends ICmdTeamRestService{
}
