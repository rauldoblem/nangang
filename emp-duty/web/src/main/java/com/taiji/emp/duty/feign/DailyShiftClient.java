package com.taiji.emp.duty.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-duty",path = "api/dailyshift")
public interface DailyShiftClient extends IDailyShiftRestService{

}
