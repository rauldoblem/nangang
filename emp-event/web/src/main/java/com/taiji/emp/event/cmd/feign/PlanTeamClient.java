package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.res.feign.IPlanTeamRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-res",path = "api/planTeams")
public interface PlanTeamClient extends IPlanTeamRestService{
}
