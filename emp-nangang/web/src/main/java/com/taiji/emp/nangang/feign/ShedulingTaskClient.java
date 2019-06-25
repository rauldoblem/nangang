package com.taiji.emp.nangang.feign;

import com.taiji.emp.duty.feign.IScheduldeTaskRestService;
import com.taiji.emp.duty.feign.ISchedulingRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/11 15:03
 */
@FeignClient(value = "base-server-zuul/micro-emp-duty",path = "api/schedulingTask")
public interface ShedulingTaskClient extends IScheduldeTaskRestService {
}
