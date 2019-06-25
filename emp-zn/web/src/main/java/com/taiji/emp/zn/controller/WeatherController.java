package com.taiji.emp.zn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.emp.zn.common.constant.ZNGlobal;
import com.taiji.emp.zn.util.HttpClientUtil;
import com.taiji.emp.zn.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/21 17:31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weathers")
public class WeatherController {

    @Autowired
    ObjectMapper mapper;
    @GetMapping
    public ResultEntity getWeather() throws IOException {

        String url = ZNGlobal.GET_WEATHER_URL + ZNGlobal.HANGZHOU;
        String result = HttpClientUtil.httpGet(url);
        WeatherVo resultVo = new WeatherVo();
        TotalWeatherVo totalWeatherVo = mapper.readValue(result, TotalWeatherVo.class);
        if(null != totalWeatherVo){
            resultVo = totalWeatherVo.getData();
            if(null != resultVo){
                //拼装数据"description": "今日天气实况：气温：12℃；风向/风力：西北风 1级；湿度：95%"
                //To "description":"风向/风力：西北风 1级；湿度：95%" "temperature":"12℃"
                String description = resultVo.getDescription();
                if(null != description){
                    String[] split1 = description.split("；");
                    if(null != split1 && split1.length == 3){
                        String temperature = split1[0].substring(10);
                        resultVo.setTemperature(temperature);
                        resultVo.setDescription(split1[1] + "；" + split1[2]);
                    }
                }
                //拼装数据
                List<WeatherInfoVo> weathers = resultVo.getWeathers();
                if(null != weathers){
                    for (WeatherInfoVo weather : weathers) {
                        String meteorology = weather.getMeteorology();
                        //将"12月23日 阴" 分成 "12月23日" "阴"
                        String[] split = meteorology.split(" ");
                        if(split.length == 2 && null != split){
                            weather.setMeteorology(split[0]);
                            weather.setType(split[1]);
                        }
                    }
                }
            }
        }
        return ResultUtils.success(resultVo);
    }
}
