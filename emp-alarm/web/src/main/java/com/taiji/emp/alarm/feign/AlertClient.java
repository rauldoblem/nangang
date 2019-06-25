package com.taiji.emp.alarm.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-alarm", path ="api/alerts")
public interface AlertClient extends IAlertRestService{
}
