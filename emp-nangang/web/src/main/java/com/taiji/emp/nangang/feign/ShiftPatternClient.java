package com.taiji.emp.nangang.feign;

import com.taiji.emp.duty.feign.IShiftPatternRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-duty",path = "api/shiftPatterns")
public interface ShiftPatternClient extends IShiftPatternRestService {
}
