package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.WasteWater;
import com.taiji.emp.nangang.feign.IWasteWaterService;
import com.taiji.emp.nangang.mapper.WasteWaterMapper;
import com.taiji.emp.nangang.service.WasteWaterService;
import com.taiji.emp.nangang.vo.WasteWaterVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/10 11:26
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/wasteWaters")
public class WasteWaterController implements IWasteWaterService{

    @Autowired
    private WasteWaterService service;
    @Autowired
    private WasteWaterMapper mapper;

    @Override
    public ResponseEntity<WasteWaterVo> getWasteWater() {
        WasteWater entity = service.getWasteWater();
        WasteWaterVo vo = mapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }
}
