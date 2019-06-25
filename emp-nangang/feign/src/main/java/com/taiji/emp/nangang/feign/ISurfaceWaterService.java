package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.ConventionalFactorVo;
import com.taiji.emp.nangang.vo.SurfaceWaterVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "micro-nangang-surfaceWater")
public interface ISurfaceWaterService {
    /**
     * 查看地表水
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find")
    @ResponseBody
    ResponseEntity<SurfaceWaterVo> find();
}
