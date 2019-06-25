package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.entity.Weather;
import com.taiji.emp.nangang.feign.IWeatherService;
import com.taiji.emp.nangang.mapper.WeatherMapper;
import com.taiji.emp.nangang.service.WeatherService;
import com.taiji.emp.nangang.vo.WeatherVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/19 15:50
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/weathers")
public class WeatherController extends BaseController implements IWeatherService {

    @Autowired
    private WeatherService service;
    @Autowired
    private WeatherMapper mapper;

    @Override
    public ResponseEntity<List<WeatherVo>> findAll() {
        List<Weather> listWeather = service.fineAll();
        List<WeatherVo> vos = mapper.entityListToVoList(listWeather);
        return ResponseEntity.ok(vos);
    }

//    @Override
//    public void saveAll(List<WeatherVo> vos) {
//        List<Weather> list = mapper.voListToEntityList(vos);
//        service.saveAll(list);
//    }
}

