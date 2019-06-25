package com.taiji.emp.res.feign;

import com.taiji.base.sys.feign.IOrgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2019/1/3 20:55
 */
@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/org")
public interface OrgClient extends IOrgRestService {
}
