package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.EventLogVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-event-eventLog")
public interface IEventLogRestService {

    /**
     * 新增事件应急日志
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<EventLogVo> addEventLog(@RequestBody EventLogVo vo);

    /**
     * 删除事件应急日志
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 修改事件应急日志
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<EventLogVo> update(@RequestBody EventLogVo vo, @PathVariable(value = "id") String id);

    /**
     * 获取单条事件应急日志信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<EventLogVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 查询事件应急日志列表
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<EventLogVo>> findList(@RequestBody EventLogVo vo);
}
