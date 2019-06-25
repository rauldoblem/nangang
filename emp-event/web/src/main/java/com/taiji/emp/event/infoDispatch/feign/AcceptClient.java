package com.taiji.emp.event.infoDispatch.feign;

import com.taiji.emp.event.infoConfig.feign.IAcceptInformService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/infoMags")
public interface AcceptClient extends IAcceptRestService{
}
