package com.taiji.emp.event.infoConfig.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-event",path = "api/acceptRules")
public interface AcceptRuleClient extends IAcceptRuleService{
}
