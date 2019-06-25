package com.taiji.emp.zn.feign;

import com.taiji.emp.zn.vo.PushAlertVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yhcookie
 * @date 2018/12/22 20:47
 */
@FeignClient(value = "micro-zn-znAlerts")
public interface IZNAlertRestService {

    @RequestMapping(method = RequestMethod.POST, path = "/saveAlert")
    @ResponseBody
    ResponseEntity<PushAlertVo> savePushAlert(@RequestBody PushAlertVo pushAlertVo);
}
