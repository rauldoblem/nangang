package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.WeatherClient;
import com.taiji.emp.nangang.vo.WeatherVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/19 15:34
 */
@Service
public class WeatherService extends BaseService{

    @Autowired
    WeatherClient client;

    /**
     * 获取天气（不用参数）
     */
    public List<WeatherVo> findAll() {

        ResponseEntity<List<WeatherVo>> all = client.findAll();
        List<WeatherVo> vos = ResponseEntityUtils.achieveResponseEntityBody(all);
        return vos;
    }
}

