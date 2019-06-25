package com.taiji.emp.nangang.feign;

import com.taiji.emp.base.feign.INoticeRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-base",path = "api/notice")
public interface NoticeClient extends INoticeRestService {
}
