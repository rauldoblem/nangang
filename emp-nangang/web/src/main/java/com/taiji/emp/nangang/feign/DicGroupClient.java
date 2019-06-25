package com.taiji.emp.nangang.feign;

import com.taiji.base.sys.feign.IDicGroupRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/dic/group")
public interface DicGroupClient extends IDicGroupRestService {
}
