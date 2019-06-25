package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.searchVo.company.CompanyListVo;
import com.taiji.emp.nangang.vo.CompanyVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = "micro-nangang-company")
public interface ICompanyService {
    @RequestMapping(method = RequestMethod.GET ,path = "/findOne")
    @ResponseBody
    ResponseEntity <List<CompanyVo>> findAll();

    /**
     * 根据参数获取SigninVo多条记录
     * 查询参数 name(可选)
     *  @param companyListVo
     *  @return ResponseEntity<List<FaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<CompanyVo>> findList(@RequestBody CompanyListVo companyListVo);

}
