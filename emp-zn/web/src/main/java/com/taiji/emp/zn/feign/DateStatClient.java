package com.taiji.emp.zn.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 按日期统计接口 feign
 */
@FeignClient(value = "base-server-zuul/micro-emp-zn", path ="api/dateStat")
public interface DateStatClient extends IDateStatRestService{
}
