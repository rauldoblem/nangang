package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.SchedulingVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(value = "micro-duty-task-scheduling")
public interface ISchedulingTaskRestService {

    /**
     * 获取下个班次的值班人员信息
     * @param schedulingVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/nextTimes/list")
    @ResponseBody
    ResponseEntity<List<SchedulingVo>> findNextTimesList(@RequestBody SchedulingVo schedulingVo);

    /**
     *  根据当前时间获得当前shiftPattId
     * @param  localDateTime
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findShiftPattId")
    @ResponseBody
    ResponseEntity<SchedulingVo> findShiftPattId(@RequestBody LocalDateTime localDateTime);
}
