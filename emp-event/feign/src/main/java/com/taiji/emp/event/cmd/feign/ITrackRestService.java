package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.searchVo.DispatchVo;
import com.taiji.emp.event.cmd.searchVo.EcTaskListVo;
import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
import com.taiji.emp.event.cmd.searchVo.TimeAxisTaskVo;
import com.taiji.emp.event.cmd.vo.trackVo.*;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 处置跟踪 应急任务 feign 接口服务类
 * @author sun yi
 * @date 2018年11月7日
 */
@FeignClient(value = "micro-event-track-task")
public interface ITrackRestService {

    /**
     * 根据参数获取 TaskVo 多条记录,不分页
     * params参数key为
     *  @return ResponseEntity<List<TaskVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<TaskVo>> findList(@RequestBody TaskPageVo taskPageVo);

    /**
     * 根据参数获取 TaskVo 多条记录,分页信息
     *          page,size
     *  @return ResponseEntity<RestPageImpl<TaskVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<TaskVo>> findPage(@RequestBody TaskPageVo taskPageVo);

    /**
     * 新增 应急任务 TaskVo，TaskVo 不能为空
     * @param vo
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<TaskVo> create(@RequestBody TaskVo vo);


    /**
     * 新增 应急任务 TaskVo，TaskVo 不能为空
     * @param vo
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/createList")
    @ResponseBody
    void createList(@RequestBody  List<TaskVo> vo);

    /**
     * 更新 应急任务 TaskVo，TaskVo 不能为空
     * @param vo,
     * @param id 要更新 TaskVo id
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<TaskVo> update(@RequestBody TaskVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取应急任务 TaskVo
     * @param id id不能为空
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<TaskVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);


    /**
     * 根据id将一条应急任务下发
     * @param vo
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/dispatch")
    @ResponseBody
    ResponseEntity<Void> dispatch(@RequestBody DispatchVo vo);

    /**
     * 根据处置方案id 查询任务信息 以时间轴显示
     * @param vo
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/timeAxis")
    @ResponseBody
    ResponseEntity<List<EcTaskListVo>> timeAxisTask(@RequestBody TimeAxisTaskVo vo);



}
