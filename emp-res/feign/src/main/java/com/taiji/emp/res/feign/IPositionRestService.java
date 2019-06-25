package com.taiji.emp.res.feign;

import com.taiji.emp.res.vo.PositionVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *仓位 feign 接口服务类
 */
@FeignClient(value = "micro-res-position")
public interface IPositionRestService {

    /**
     * 根据参数获取PositionVo多条记录
     *  @param positionVo
     *  @return ResponseEntity<List<PositionVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PositionVo>> findList(@RequestBody PositionVo positionVo);
}
