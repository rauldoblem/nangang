package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.SmokeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/10 11:29
 */
@FeignClient(value = "micro-nangang-smokes")
public interface ISmokeService {

    @RequestMapping(method = RequestMethod.GET ,path = "/getSmoke")
    @ResponseBody
    ResponseEntity<SmokeVo> getSmoke();

    /**
     * 查询多条烟气信息
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<SmokeVo>> findPage();
}
