package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.WeatherVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/19 15:37
 */
@FeignClient(value = "micro-nangang-dailyChecks")
public interface IWeatherService {

    @RequestMapping(method = RequestMethod.GET ,path = "/findOne")
    @ResponseBody
    ResponseEntity<List<WeatherVo>> findAll();

}
