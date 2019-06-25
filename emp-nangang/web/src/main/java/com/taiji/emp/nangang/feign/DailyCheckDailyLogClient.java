package com.taiji.emp.nangang.feign;

import com.taiji.emp.duty.feign.IDailyLogRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/3 11:15
 */
@FeignClient(value = "base-server-zuul/micro-emp-duty",path = "api/dailyLog")
public interface DailyCheckDailyLogClient extends IDailyLogRestService {
}
