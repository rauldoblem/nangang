package com.taiji.emp.alarm.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-alarm", path ="api/alertNotices")
public interface AlertNoticeClient extends IAlertNoticeRestService{
}
