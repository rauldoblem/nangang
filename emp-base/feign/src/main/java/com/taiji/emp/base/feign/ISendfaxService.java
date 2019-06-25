package com.taiji.emp.base.feign;

import com.taiji.emp.base.searchVo.fax.SendfaxPageVo;
import com.taiji.emp.base.vo.SendfaxVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "micro-base-sendfax")
public interface ISendfaxService {

    /**
     * 根据参数获取SendfaxVo多条记录,分页信息
     * 查询参数 title(可选),receiver(可选),faxNumber(可选),sendtimeStart(可选),sendtimeEnd(可选)
     *          page,size
     *  @param sendfaxPageVo
     *  @return ResponseEntity<RestPageImpl<RecvfaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<SendfaxVo>> findPage(@RequestBody SendfaxPageVo sendfaxPageVo);
}
