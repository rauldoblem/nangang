package com.taiji.emp.nangang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/11 14:20
 */
@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/wasteWaters")
public interface WasteWaterClient extends IWasteWaterService{
}
