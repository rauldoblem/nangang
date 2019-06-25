package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 值班模式设置表 feign 接口服务类
 */
@FeignClient(value = "micro-duty-patternSetting")
public interface IPatternSettingRestService {

    /**
     * 根据所属单位ID获取模式设置
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<List<PatternSettingVo>> findAll(@PathVariable(value = "id") String id);

    /**
     * 模式设置批量插入
     * @param  patternSettingList
     * @return ResponseEntity<List<PatternSettingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/savePatternSettings")
    @ResponseBody
    ResponseEntity<List<PatternSettingVo>> createBatch(List<PatternSettingVo> patternSettingList);

    /**
     * 根据所属单位ID,日期类型编码，获取模式设置
     * @param patternSettingVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/findOne")
    @ResponseBody
    ResponseEntity<PatternSettingVo> findOne(PatternSettingVo patternSettingVo);

    /**
     * 根据当前时间获取班次名称，供交接班使用
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/findCurrentShiftName")
    @ResponseBody
    ResponseEntity <SchedulingVo> findCurrentShiftName(@RequestParam(value = "orgId") String orgId);

}
