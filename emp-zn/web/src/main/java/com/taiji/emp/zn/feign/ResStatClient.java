package com.taiji.emp.zn.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 应急资源统计接口 feign
 */
@FeignClient(value = "base-server-zuul/micro-emp-zn", path ="api/resStat")
public interface ResStatClient extends IResStatRestService{
}
