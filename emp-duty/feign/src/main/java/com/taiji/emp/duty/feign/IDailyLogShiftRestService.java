package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.dailyShift.DailyLogShiftVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 交接班 feign 接口服务类
 */
@FeignClient(value = "micro-base-dailyLog-shift")
public interface IDailyLogShiftRestService {


    /**
     * 新增交接班内容中间表
     * @param list
     * @return ResponseEntity<DailyLogShiftVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<DailyLogShiftVo> create(@RequestBody List<DailyLogShiftVo> list);

}
