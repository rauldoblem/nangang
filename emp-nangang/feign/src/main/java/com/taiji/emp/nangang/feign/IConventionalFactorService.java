package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.ConventionalFactorVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "micro-nangang-conventionalFactor")
public interface IConventionalFactorService {
    /**
     * 查看气象因子
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find")
    @ResponseBody
    ResponseEntity<ConventionalFactorVo> find();

    /*
     * 分页查询
     */

    @RequestMapping(method = RequestMethod.POST, path = "find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<ConventionalFactorVo>> findPage();
}
