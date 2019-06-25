package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.SurfaceWaterService;
import com.taiji.emp.nangang.vo.FinalSurfaceWaterVo;
import com.taiji.emp.nangang.vo.SurfaceWaterVo;
import com.taiji.emp.nangang.vo.TotalSurfaceWaterVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/surfaceWaters")
public class SurfaceWaterController {
    @Autowired
    private SurfaceWaterService service;
    @GetMapping("/getSurfaceWater")
    public ResultEntity getConventionalFactor(){
        SurfaceWaterVo vo = service.getSurfaceWater();


        TotalSurfaceWaterVo vo1 = new TotalSurfaceWaterVo("水温","℃");
        vo1.setValue(vo.getWaterTemperature());
        TotalSurfaceWaterVo vo2 = new TotalSurfaceWaterVo("PH值","");
        vo2.setValue(vo.getPh());
        TotalSurfaceWaterVo vo3 = new TotalSurfaceWaterVo("溶解氧","mg/L");
        vo3.setValue(vo.getDissolvedOxygen());
        TotalSurfaceWaterVo vo4 = new TotalSurfaceWaterVo("浊度","NTU");
        vo4.setValue(vo.getTurbidity());
        TotalSurfaceWaterVo vo5 = new TotalSurfaceWaterVo("电导率","ms/cm");
        vo5.setValue(vo.getConductivity());
        TotalSurfaceWaterVo vo6 = new TotalSurfaceWaterVo("氨氮","mg/L");
        vo6.setValue(vo.getAmmoniaNitrogen());
        TotalSurfaceWaterVo vo7 = new TotalSurfaceWaterVo("总磷","mg/L");
        vo7.setValue(vo.getTotalPhosphorus());
        TotalSurfaceWaterVo vo8 = new TotalSurfaceWaterVo("总氮","mg/L");
        vo8.setValue(vo.getTotalNitrogen());

        List<TotalSurfaceWaterVo> totalSurfaceWaterVos = new ArrayList<>();
        totalSurfaceWaterVos.add(vo1);
        totalSurfaceWaterVos.add(vo2);
        totalSurfaceWaterVos.add(vo3);
        totalSurfaceWaterVos.add(vo4);
        totalSurfaceWaterVos.add(vo5);
        totalSurfaceWaterVos.add(vo6);
        totalSurfaceWaterVos.add(vo7);
        totalSurfaceWaterVos.add(vo8);

        FinalSurfaceWaterVo finalSurfaceWaterVo = new FinalSurfaceWaterVo();
        finalSurfaceWaterVo.setTotalSurfaceWaterVo(totalSurfaceWaterVos);
        String createTime = DateUtil.getDateTimeStr(vo.getCreateTime());
        String time = DateUtil.stringToString(createTime,"yyyy-MM-dd HH:mm");
        finalSurfaceWaterVo.setCreateTime(time);
        return ResultUtils.success(finalSurfaceWaterVo);
    }
}
