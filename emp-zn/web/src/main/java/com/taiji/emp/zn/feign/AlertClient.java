package com.taiji.emp.zn.feign;

import com.taiji.emp.alarm.feign.IAlertRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-alarm", path ="api/alerts")
public interface AlertClient extends IAlertRestService{
}
