package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.ConventionalFactorClient;
import com.taiji.emp.nangang.feign.MeteorologicalClient;
import com.taiji.emp.nangang.feign.WeatherClient;
import com.taiji.emp.nangang.searchVo.meteorological.MeteorologicalPageVo;
import com.taiji.emp.nangang.vo.ConventionalFactorVo;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import com.taiji.emp.nangang.vo.WeatherVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MeteorologicalService {
    @Autowired
    private MeteorologicalClient meteorologicalClient;
    @Autowired
    private ConventionalFactorClient conventionalFactorClient;
    @Autowired
    private WeatherClient weatherClient;

    public MeteorologicalVo getMeteorological() {
        ResponseEntity<MeteorologicalVo> result = meteorologicalClient.find();
        MeteorologicalVo meteorologicalVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        //查到conventionalFactor里的三个属性，meteorologicalVo
        ResponseEntity<ConventionalFactorVo> result2 = conventionalFactorClient.find();
        ConventionalFactorVo conventionalFactorVo = ResponseEntityUtils.achieveResponseEntityBody(result2);
        meteorologicalVo.setPm2_5(conventionalFactorVo.getPm2_5());
        meteorologicalVo.setCo(conventionalFactorVo.getCo());
        meteorologicalVo.setNo2(conventionalFactorVo.getNo2());
        meteorologicalVo.setPm10(conventionalFactorVo.getPm10());
        return meteorologicalVo;
    }

//    public RestPageImpl<MeteorologicalVo> findPage(){
//        ResponseEntity<RestPageImpl<MeteorologicalVo>> resultVo = meteorologicalClient.findPage();
//        RestPageImpl<MeteorologicalVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
//        return vo;
//    }


    /*
     *  获取天气预报信息，取数据库中最新时间的天气情况
     */
    public WeatherVo getLatestWeather() {

        WeatherVo weatherVo = new WeatherVo();

        //获取最新天气情况
        ResponseEntity<MeteorologicalVo> result = meteorologicalClient.find();
        MeteorologicalVo meteorologicalVo = ResponseEntityUtils.achieveResponseEntityBody(result);

        ResponseEntity<List<WeatherVo>> weatherVoList = weatherClient.findAll();
        List<WeatherVo>  voLists = ResponseEntityUtils.achieveResponseEntityBody(weatherVoList);

        if (!CollectionUtils.isEmpty(voLists)){
            weatherVo = voLists.get(0);
        }
        weatherVo.setHumidity(meteorologicalVo.getHumidity());
        weatherVo.setTemperature(meteorologicalVo.getTemperature());
        return weatherVo;
    }


}
