package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.infoDispatch.feign.IEventRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/events")
public interface EventClient extends IEventRestService{
}
