package com.taiji.emp.nangang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/11 10:10
 */
@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/smokes")
public interface SmokeClient extends ISmokeService{
}
