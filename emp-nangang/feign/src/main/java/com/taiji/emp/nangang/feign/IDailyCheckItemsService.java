package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-nangang-dailyCheckItems")
public interface IDailyCheckItemsService {

    @RequestMapping(method = RequestMethod.POST ,path = "/update")
    @ResponseBody
    ResponseEntity<DailyCheckItemsVo> update(@RequestBody DailyCheckItemsVo vo);

    @RequestMapping(method = RequestMethod.GET ,path = "/findOne/{id}")
    @ResponseBody
    ResponseEntity<List<DailyCheckItemsVo>> findCheckId(@PathVariable(value = "id")String id);

    @RequestMapping(method = RequestMethod.GET ,path = "/findDailyLogId/{dailyCheckItemsVoId}")
    @ResponseBody
    ResponseEntity<String> findDailyLogId(@PathVariable(value = "dailyCheckItemsVoId") String dailyCheckItemsVoId);
}
