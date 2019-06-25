package com.taiji.emp.nangang.feign;

import com.taiji.base.sys.feign.IOrgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/org")
public interface OrgClient extends IOrgRestService {
}
