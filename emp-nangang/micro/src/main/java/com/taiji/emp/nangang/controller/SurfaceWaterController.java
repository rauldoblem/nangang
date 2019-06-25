package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.SurfaceWater;
import com.taiji.emp.nangang.feign.ISurfaceWaterService;
import com.taiji.emp.nangang.mapper.SurfaceWaterMapper;
import com.taiji.emp.nangang.service.SurfaceWaterService;
import com.taiji.emp.nangang.vo.SurfaceWaterVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/surfaceWaters")
@AllArgsConstructor
public class SurfaceWaterController  extends BaseController implements ISurfaceWaterService {
    SurfaceWaterService surfaceWaterService;
    SurfaceWaterMapper surfaceWaterMapper;
    @Override
    public ResponseEntity<SurfaceWaterVo> find() {
        SurfaceWater result = surfaceWaterService.getSurfaceWater();
        SurfaceWaterVo resultVo = surfaceWaterMapper.entityToVo(result);
        return new ResponseEntity(resultVo,HttpStatus.OK);
    }
}
