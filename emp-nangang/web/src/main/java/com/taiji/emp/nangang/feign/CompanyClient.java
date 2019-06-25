package com.taiji.emp.nangang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/companys")
public interface CompanyClient extends ICompanyService{
}
