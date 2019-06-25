package com.taiji.emp.nangang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/6 15:01
 */
@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/passRecordVehicles")
public interface PassRecordVehicleClient extends IPassRecordVehicleService{
}
