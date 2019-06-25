package com.taiji.emp.nangang.feign;

import com.taiji.emp.base.feign.IDocAttRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-base", path ="api/files")
public interface DocAttClient extends IDocAttRestService {
}
