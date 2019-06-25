package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.CmdTaskFeedBackService;
import com.taiji.emp.event.cmd.vo.TaskFeedbackSaveVo;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.infoConfig.controller.BaseController;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cmd/taskfeedback")
public class CmdTaskFeedBackController extends BaseController {

    private CmdTaskFeedBackService service;
    /**
     * 新增应急任务反馈
     */
    @PostMapping
    public ResultEntity createFeedBack(
            @Validated
            @NotNull(message = "TaskFeedbackVo 不能为null")
            @RequestBody TaskFeedbackSaveVo taskFeedbackSaveVo, Principal principal){
        service.create(taskFeedbackSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 督办任务反馈信息（含附件）
     */
    @PostMapping(path = "/tasksupervise")
    public ResultEntity tasksupervise(
            @Validated
            @NotNull(message = "TaskFeedbackVo 不能为null")
            @RequestBody TaskFeedbackSaveVo taskFeedbackSaveVo, Principal principal){
        service.tasksupervise(taskFeedbackSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据任务id获取 对应的所以反馈信息
     */
    @GetMapping
    public ResultEntity findTaskByTaskId(
            @NotEmpty(message = "taskId不能为空")
            @RequestParam(value = "taskId") String taskId){
        List<TaskFeedbackVo> vo = service.findListByTaskId(taskId);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id 获取单条反馈信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        TaskFeedbackVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

}
