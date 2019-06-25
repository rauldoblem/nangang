package com.taiji.emp.screenShow.feign;

import com.taiji.emp.event.cmd.feign.ISchemeRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/cmd/schemes")
public interface SchemeClient extends ISchemeRestService{
}
