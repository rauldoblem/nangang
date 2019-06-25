package com.taiji.emp.screenShow.feign;

import com.taiji.emp.event.infoConfig.feign.IAcceptRuleService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/acceptRules")
public interface AcceptRuleClient extends IAcceptRuleService{
}
