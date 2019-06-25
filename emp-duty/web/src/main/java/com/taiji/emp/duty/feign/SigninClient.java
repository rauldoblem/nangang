package com.taiji.emp.duty.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/signins")
public interface SigninClient extends ISignin {
}
