package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.WasteWaterService;
import com.taiji.emp.nangang.vo.TotalWasteWater;
import com.taiji.emp.nangang.vo.WasteWaterVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/11 14:16
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wasteWaters")
public class WasteWaterController {

    @Autowired
    private WasteWaterService service;
    @GetMapping("/getWasteWater")
    public ResultEntity getWasteWater(){
        WasteWaterVo vo = service.getWasteWater();
//        TotalWasteWater totalWasteWater = new TotalWasteWater();
//        List<String> data = totalWasteWater.getData();
//        data.add(vo.getPh());
//        data.add(vo.getCod());
//        data.add(vo.getAmmoniaNitrogen());
//        data.add(vo.getTotalNitrogen());
//        data.add(vo.getTotalPhosphorus());
        return ResultUtils.success(vo);
    }
}
