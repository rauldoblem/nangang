package com.taiji.emp.base.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/29 15:24
 */
@FeignClient(value = "base-server-zuul/micro-emp-base",path = "api/conditionSet")
public interface ConditionSettingClient extends IConditionSettingRestService{
}
