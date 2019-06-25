package com.taiji.emp.nangang.feign;

import com.taiji.emp.duty.feign.ISchedulingTaskRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-duty",path = "api/scheduling/task")
public interface SchedulingTaskClient extends ISchedulingTaskRestService {
}
