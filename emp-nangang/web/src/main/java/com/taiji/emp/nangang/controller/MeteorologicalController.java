package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.MeteorologicalService;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import com.taiji.emp.nangang.vo.TotalMeteorologicalVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafTemplateAvailabilityProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/meteorologicals")
public class MeteorologicalController {
    @Autowired
    private MeteorologicalService meteorologicalService;


    /**
     * 返回一条数据
     * @return
     */
    @GetMapping("/getMeteorological")
    public ResultEntity getMeteorological(){
        MeteorologicalVo vo = meteorologicalService.getMeteorological();
        return ResultUtils.success(vo);
    }

    /**
     * 分页查询
     */

//    /**
//     * 查询固定条数数据 在micro层固定
//     * 改了页面原型 暂时不用了
//     * @return
//     */
//    @PostMapping(path = "/search")
//    public ResultEntity findMeteorological(){
//        RestPageImpl<MeteorologicalVo> pageVo = meteorologicalService.findPage();
//
//        TotalMeteorologicalVo totalMeteorologicalVo = new TotalMeteorologicalVo();
//        List<String> temperature = totalMeteorologicalVo.getTemperature();
//        List<String> time = totalMeteorologicalVo.getTime();
//
//        List<MeteorologicalVo> content = pageVo.getContent();
//        for (int i = content.size() - 1; i >= 0; i--) {
//            temperature.add(content.get(i).getTemperature());
//            time.add(content.get(i).getCreateTime());
//        }
//        return ResultUtils.success(totalMeteorologicalVo);
//    }

}
