package com.taiji.emp.event.eva.feign;

import com.taiji.emp.event.eva.vo.EventEvaScoreVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-event-evaScore")
public interface IEventEvaScoreRestService {
    /**
     * 新增评估项目和关联表
     * @param evaScoreList
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<List<EventEvaScoreVo>> create(@RequestBody List<EventEvaScoreVo> evaScoreList);

    /**
     * 更新评估项目和关联表
     * @param evaScoreList
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    @ResponseBody
    ResponseEntity<List<EventEvaScoreVo>>  update(@RequestBody List<EventEvaScoreVo> evaScoreList);

    /**
     * 根据reportId查询评估项目、报告关联信息
     * @param reportId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{reportId}")
    @ResponseBody
    ResponseEntity<List<EventEvaScoreVo>> findByReportId(@PathVariable(value = "reportId") String reportId);
}
