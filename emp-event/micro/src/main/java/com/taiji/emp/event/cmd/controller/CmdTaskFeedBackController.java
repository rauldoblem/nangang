package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.track.TaskFeedback;
import com.taiji.emp.event.cmd.feign.ITaskFeedBackRestService;
import com.taiji.emp.event.cmd.mapper.CmdTaskFeedbackMapper;
import com.taiji.emp.event.cmd.service.CmdTaskFeedBackService;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/task/feed/back")
public class CmdTaskFeedBackController extends BaseController implements ITaskFeedBackRestService {

    CmdTaskFeedbackMapper mapper;
    CmdTaskFeedBackService service;


    /**
     * 新增 任务反馈 TaskFeedbackVo，TaskFeedbackVo 不能为空
     * @param vo
     * @return ResponseEntity<TaskFeedbackVo>
     */
    @Override
    public ResponseEntity<TaskFeedbackVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody TaskFeedbackVo vo) {
        TaskFeedback entity = mapper.voToEntity(vo);
        TaskFeedback result = service.create(entity);
        TaskFeedbackVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id 获取应急任务对于反馈信息 TaskFeedbackVo
     * @param
     * @return ResponseEntity<List < TaskFeedbackVo>>
     */
    @Override
    public ResponseEntity<List<TaskFeedbackVo>> findListByTaskId(
            @NotEmpty(message = "taskId 不能为空字符串")
            @RequestParam(value = "taskId") String taskId) {
        List<TaskFeedback> entity = service.findList(taskId);
        List<TaskFeedbackVo> restVo= mapper.entityListToVoList(entity);
        return ResponseEntity.ok(restVo);
    }

    /**
     * 根据id 获取应急任务对于反馈信息 TaskFeedbackVo(含附件)
     * @param id id不能为空
     * @return ResponseEntity<TaskFeedbackVo>
     */
    @Override
    public ResponseEntity<TaskFeedbackVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        TaskFeedback entity = service.findOne(id);
        TaskFeedbackVo taskFeedbackVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(taskFeedbackVo);
    }
}
