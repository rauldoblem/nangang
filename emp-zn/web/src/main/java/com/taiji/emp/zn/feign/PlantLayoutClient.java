package com.taiji.emp.zn.feign;

import com.taiji.emp.base.feign.IPlantLayoutRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-emp-base",path = "api/plantLayout")
public interface PlantLayoutClient extends IPlantLayoutRestService {
}
