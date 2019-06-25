package com.taiji.emp.screenShow.feign;

import com.taiji.emp.event.cmd.feign.ICmdOrgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/emgorgs")
public interface CmdOrgClient extends ICmdOrgRestService{
}
