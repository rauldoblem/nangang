package com.taiji.emp.event.eva.feign;

import com.taiji.emp.event.eva.vo.EventEvaReportVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "micro-event-report")
public interface IEventEvaReportRestService {

    /**
     * 新增事件评估报告
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<EventEvaReportVo> create(@RequestBody EventEvaReportVo vo);

    /**
     * 获取单条 事件评估报告信息（含各评估细项分数）
     * @param eventId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{eventId}")
    @ResponseBody
    ResponseEntity<EventEvaReportVo> findOne(@PathVariable(value = "eventId") String eventId);

    /**
     * 修改事件评估报告信息（含各评估细项分数），若不是暂存即直接评估完成需要更新事件处置状态
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    @ResponseBody
    ResponseEntity<EventEvaReportVo> update(@RequestBody EventEvaReportVo vo);
}
