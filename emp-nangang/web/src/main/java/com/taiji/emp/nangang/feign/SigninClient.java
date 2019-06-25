package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.feign.ISignin;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/signins")
public interface SigninClient extends ISignin {
}
