package com.taiji.emp.event.eva.feign;

import com.taiji.emp.event.eva.vo.EventEvaProcessNodeVo;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 过程再现 feign 接口服务类
 * @author sun yi
 * @date 2018年10月30日
 */
@FeignClient(value = "micro-event-process")
public interface IEventEvaProcessRestService {

    /**
     * 根据参数获取 EventEvaProcessVo 多条记录,不分页
     * params参数key为 orgId(部门ID)
     *  @return ResponseEntity<List<EventEvaProcessVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<EventEvaProcessVo>> findList(@RequestParam(value = "eventId") String eventId);



    /**
     * 新增 过程再现 EventEvaProcessVo，EventEvaProcessVo 不能为空
     * @param vo
     * @return ResponseEntity<EventEvaProcessVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<EventEvaProcessVo> create(@RequestBody EventEvaProcessVo vo);

    /**
     * 更新 过程再现 EventEvaProcessVo，EventEvaProcessVo 不能为空
     * @param vo,
     * @param id 要更新 EventEvaProcessVo id
     * @return ResponseEntity<EventEvaProcessVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<EventEvaProcessVo> update(@RequestBody EventEvaProcessVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取过程再现 EventEvaProcessVo
     * @param id id不能为空
     * @return ResponseEntity<EventEvaProcessVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<EventEvaProcessVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);


}
