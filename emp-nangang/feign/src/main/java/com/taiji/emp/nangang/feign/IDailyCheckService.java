package com.taiji.emp.nangang.feign;

import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.emp.nangang.searchVo.dailyCheck.DailyCheckPageVo;
import com.taiji.emp.nangang.vo.DailyCheckDailyLogVo;
import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import com.taiji.emp.nangang.vo.DailyCheckVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-nangang-dailyChecks")
public interface IDailyCheckService {

    @RequestMapping(method = RequestMethod.POST ,path = "/updateDailyCheck")
    @ResponseBody
    ResponseEntity<DailyCheckVo> updateDailyCheck(@RequestParam("id") String id);

    @RequestMapping(method = RequestMethod.POST ,path = "/selectItem")
    @ResponseBody
    ResponseEntity<List<DailyCheckItemsVo>> selectItem(@RequestBody DailyCheckVo vo);

    /**
     * 根据检查日期的范围获取分页DailyCheckVo多条记录
     * param参数key为checkDateStart(可选),checkDateEnd(可选)，page（可选），size（not less than 1）
     * @return ResponseEntity<RestPageImpl<DailyCheckVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DailyCheckVo>> findPage(@RequestBody DailyCheckPageVo dailyCheckPageVo);

    @RequestMapping(method = RequestMethod.POST ,path = "/exists")
    @ResponseBody
    DailyCheckVo exists(@RequestBody DailyCheckVo vo);

    @RequestMapping(method = RequestMethod.POST ,path = "/save")
    @ResponseBody
    ResponseEntity<DailyCheckVo> save(@RequestBody DailyCheckVo dailyCheckVo);

    @RequestMapping(method = RequestMethod.POST ,path = "/findOne")
    @ResponseBody
    ResponseEntity<DailyCheckVo> findOne(@RequestBody DailyCheckVo vo);

    @RequestMapping(method = RequestMethod.POST ,path = "/saveByList")
    @ResponseBody
    ResponseEntity<List<DailyCheckItemsVo>> saveByList(@RequestBody List<DailyCheckItemsVo> dailyCheckItems);

    @RequestMapping(method = RequestMethod.POST ,path = "/addDailyLog")
    @ResponseBody
    ResponseEntity<DailyCheckDailyLogVo> addDailyLog(@RequestBody DailyCheckDailyLogVo dailyCheckDailyLogVo);
}
