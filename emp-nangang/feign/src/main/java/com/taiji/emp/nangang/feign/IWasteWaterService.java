package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.SmokeVo;
import com.taiji.emp.nangang.vo.WasteWaterVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/10 11:30
 */
@FeignClient(value = "micro-nangang-wasteWaters")
public interface IWasteWaterService {

    @RequestMapping(method = RequestMethod.GET ,path = "/getWasteWater")
    @ResponseBody
    ResponseEntity<WasteWaterVo> getWasteWater();
}
