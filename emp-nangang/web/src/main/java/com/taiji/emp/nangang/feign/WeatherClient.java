package com.taiji.emp.nangang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/11/19 15:36
 */
@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/weathers")
public interface WeatherClient extends IWeatherService{
}
