package com.taiji.emp.zn.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/22 20:45
 */
@FeignClient(value = "base-server-zuul/micro-emp-zn", path ="api/alerts")
public interface ZNAlertClient extends IZNAlertRestService{
}
