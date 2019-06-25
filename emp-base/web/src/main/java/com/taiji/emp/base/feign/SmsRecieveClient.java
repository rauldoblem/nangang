package com.taiji.emp.base.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-base",path = "api/smsRecieves")
public interface SmsRecieveClient extends ISmsRecieveService{
}
