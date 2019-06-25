package com.taiji.emp.event.eva.feign;

import com.taiji.emp.event.eva.vo.EventEvaItemVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-event-evaItem")
public interface IEventEvaItemRestService {
    /**
     * 新增事件评估项
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<EventEvaItemVo> create(@RequestBody EventEvaItemVo vo);

    /**
     * 删除事件评估项信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 修改事件评估项信息
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<EventEvaItemVo> update(@RequestBody EventEvaItemVo vo, @PathVariable(value = "id") String id);

    /**
     * 获取单条事件评估项信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<EventEvaItemVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据事件类型获取事件评估项列表
     * @param eventTypeId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<EventEvaItemVo>> findListByEventTypeId(@RequestParam(name = "eventTypeId") String eventTypeId);
}
