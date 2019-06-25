package com.taiji.emp.screenShow.feign;

import com.taiji.emp.event.infoConfig.feign.IAcceptInformService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/acceptInforms")
public interface AcceptInformClient extends IAcceptInformService{
}
