package com.taiji.emp.zn.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 事件信息统计接口 feign
 */
@FeignClient(value = "base-server-zuul/micro-emp-zn", path ="api/zn/info")
public interface InfoClient extends IInfoRestService{
}
