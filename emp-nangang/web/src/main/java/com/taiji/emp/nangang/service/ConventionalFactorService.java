package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.ConventionalFactorClient;
import com.taiji.emp.nangang.feign.MeteorologicalClient;
import com.taiji.emp.nangang.vo.ConventionalFactorVo;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConventionalFactorService {

    @Autowired
    private ConventionalFactorClient conventionalFactorClient;

//    @Autowired
//    private MeteorologicalClient meteorologicalClient;

    public ConventionalFactorVo getConventionalFactor() {

        ResponseEntity<ConventionalFactorVo> result1 = conventionalFactorClient.find();
        ConventionalFactorVo conventionalFactorVo = ResponseEntityUtils.achieveResponseEntityBody(result1);
//        //查到meteorological里的三个属性，赋给conventionalFactorVo
//        ResponseEntity<MeteorologicalVo> result2 = meteorologicalClient.find();
//        MeteorologicalVo meteorologicalVo = ResponseEntityUtils.achieveResponseEntityBody(result2);
//        conventionalFactorVo.setAtmosphericPressure(meteorologicalVo.getAtmosphericPressure());
//        conventionalFactorVo.setWindDirection(meteorologicalVo.getWindDirection());
//        conventionalFactorVo.setRainfall(meteorologicalVo.getRainfall());
        return conventionalFactorVo;
    }
    public RestPageImpl<ConventionalFactorVo> findPage(){
        ResponseEntity<RestPageImpl<ConventionalFactorVo>> resultVo = conventionalFactorClient.findPage();
        RestPageImpl<ConventionalFactorVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }
}
