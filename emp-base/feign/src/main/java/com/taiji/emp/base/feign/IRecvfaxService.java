package com.taiji.emp.base.feign;

import com.taiji.emp.base.searchVo.fax.RecvfaxPageVo;
import com.taiji.emp.base.vo.RecvfaxVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@FeignClient(value = "micro-base-recvfax")
public interface IRecvfaxService {

    /**
     * 根据参数获取RecvfaxVo多条记录,分页信息
     * 查询参数 title(可选),sender(可选),faxNumber(可选),recvtimeStart(可选),recvtimeEnd(可选)
     *          page,size
     *  @param recvfaxPageVo
     *  @return ResponseEntity<RestPageImpl<RecvfaxVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<RecvfaxVo>> findPage(@RequestBody RecvfaxPageVo recvfaxPageVo);

}
