package com.taiji.emp.nangang.feign;

import com.taiji.base.sys.feign.IUserRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/user")
public interface UserClient extends IUserRestService {
}
