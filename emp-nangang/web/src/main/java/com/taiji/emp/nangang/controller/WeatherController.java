package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.MeteorologicalService;
import com.taiji.emp.nangang.service.WeatherService;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import com.taiji.emp.nangang.vo.WeatherVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/19 15:26
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weathers")
public class WeatherController extends BaseController{

    @Autowired
    private MeteorologicalService service;
    /**
     * 获取天气预报信息，取数据库中最新时间的天气情况
     */
    @GetMapping
    public ResultEntity getWeather() {

        WeatherVo weatherVo = service.getLatestWeather();
        return ResultUtils.success(weatherVo);
    }
}
