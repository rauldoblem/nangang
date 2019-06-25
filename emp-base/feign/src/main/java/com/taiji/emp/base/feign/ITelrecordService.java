package com.taiji.emp.base.feign;


import com.taiji.emp.base.searchVo.telrecord.TelrecordPageVo;
import com.taiji.emp.base.vo.TelrecordVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "micro-base-telrecord")
public interface ITelrecordService {
    /**
     * 根据参数获取TelrecordVo多条记录，分页信息
     * params参数key为caller(可选),callee(可选),fileName(可选),beginTime(可选)
     *              page,siza
     * @param telrecordPageVo
     * @return ReponseEntity<RestPageImpl<TelrecordVo></>></>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<TelrecordVo>> findPage(@RequestBody TelrecordPageVo telrecordPageVo);
}
