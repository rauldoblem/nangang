package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.searchVo.CalenSettingListVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日历设置表 feign 接口服务类
 */
@FeignClient(value = "micro-duty-calenSetting")
public interface ICalenSettingRestService {

    /**
     * 日历设置查询
     * @param  calenSettingVo
     * @return ResponseEntity<CalenSettingVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/searchAll")
    @ResponseBody
    ResponseEntity<List<CalenSettingVo>> findAll(@RequestBody CalenSettingVo calenSettingVo);

    /**
     * 日历设置新增
     * @param  calenSettingListVo
     * @return ResponseEntity<CalenSettingVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/saveCalen")
    @ResponseBody
    ResponseEntity<CalenSettingVo> create(@RequestBody CalenSettingListVo calenSettingListVo);

    /**
     * 获取值班模式
     * @param dateStr
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/setting/date")
    @ResponseBody
    ResponseEntity<CalenSettingVo> findSettingDate(@RequestParam(value = "dateStr") String dateStr,@RequestParam(value = "orgId") String orgId);

    /**
     * 日历设置批量插入
     * @param  calenSettingListVo
     * @return ResponseEntity<List<CalenSettingVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/saveBatch")
    @ResponseBody
    ResponseEntity<List<CalenSettingVo>> createBatch(@RequestBody CalenSettingListVo calenSettingListVo);
}
