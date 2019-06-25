package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.searchVo.DutyMan;
import com.taiji.emp.duty.searchVo.SearchAllDutyVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 值班表 feign 接口服务类
 */
@FeignClient(value = "micro-duty-scheduldeTask")
public interface IScheduldeTaskRestService {


    /**
     *  根据当前时间获得当前shiftPattId
     * @param  localDateTime
     * @return ResponseEntity<List<SchedulingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findShiftPattId")
    @ResponseBody
    ResponseEntity<SchedulingVo> findShiftPattId(@RequestBody LocalDateTime localDateTime);

}
