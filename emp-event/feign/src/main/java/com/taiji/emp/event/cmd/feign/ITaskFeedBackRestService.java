package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 处置跟踪 反馈 feign 接口服务类
 * @author sun yi
 * @date 2018年11月9日
 */
@FeignClient(value = "micro-event-task-feed-back")
public interface ITaskFeedBackRestService {

    /**
     * 新增 任务反馈 TaskFeedbackVo，TaskFeedbackVo 不能为空
     * @param vo
     * @return ResponseEntity<TaskFeedbackVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<TaskFeedbackVo> create(@RequestBody TaskFeedbackVo vo);


    /**
     * 根据id 获取应急任务对于反馈信息 TaskFeedbackVo
     * @param taskId taskId不能为空
     * @return ResponseEntity<List<TaskFeedbackVo>>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find")
    @ResponseBody
    ResponseEntity<List<TaskFeedbackVo>> findListByTaskId(@RequestParam(value = "taskId") String taskId);

    /**
     * 根据id 获取应急任务对于反馈信息 TaskFeedbackVo(含附件)
     * @param id id不能为空
     * @return ResponseEntity<TaskFeedbackVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<TaskFeedbackVo> findOne(@PathVariable(value = "id") String id);


}
