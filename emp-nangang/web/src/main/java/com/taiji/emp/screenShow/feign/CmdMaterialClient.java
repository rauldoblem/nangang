package com.taiji.emp.screenShow.feign;

import com.taiji.emp.event.cmd.feign.ICmdMaterialRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/materials")
public interface CmdMaterialClient extends ICmdMaterialRestService{
}
