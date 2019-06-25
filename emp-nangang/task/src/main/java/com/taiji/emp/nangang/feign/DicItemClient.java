package com.taiji.emp.nangang.feign;

import com.taiji.base.sys.feign.IDicGroupItemRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/dic/groupItem")
public interface DicItemClient extends IDicGroupItemRestService {
}

