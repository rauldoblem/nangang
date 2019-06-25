package com.taiji.emp.nangang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author yhcookie
 * @date 2018/12/20 11:15
 */
@FeignClient(value="base-server-zuul/micro-emp-nangang",path = "api/companyInfo")
public interface CompanyInfoClient extends ICompanyInfoService{
}
