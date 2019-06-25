package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.CompanyInfoVo;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/20 11:18
 */
@FeignClient(value = "micro-nangang-companyInfo")
public interface ICompanyInfoService {

    /**
     * 公司信息
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getCompanyInfo")
    @ResponseBody
    ResponseEntity<List<CompanyInfoVo>> getCompanyInfo();
}
