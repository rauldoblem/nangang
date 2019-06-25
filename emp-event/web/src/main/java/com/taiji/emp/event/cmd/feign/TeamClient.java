package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.ITeamRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/teams")
public interface TeamClient extends ITeamRestService{
}
