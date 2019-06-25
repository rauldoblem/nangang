package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.SurfaceWaterClient;
import com.taiji.emp.nangang.vo.SurfaceWaterVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SurfaceWaterService {
    @Autowired
    private SurfaceWaterClient surfaceWaterClient;

    public SurfaceWaterVo getSurfaceWater() {
        ResponseEntity<SurfaceWaterVo> result = surfaceWaterClient.find();
        SurfaceWaterVo surfaceWaterVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return surfaceWaterVo;
    }
}
