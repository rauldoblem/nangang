package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.searchVo.*;
import com.taiji.emp.event.cmd.service.CmdTaskService;
import com.taiji.emp.event.cmd.vo.trackVo.*;
import com.taiji.emp.event.infoConfig.controller.BaseController;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/cmd/tasks")
public class CmdTaskController extends BaseController {

    private CmdTaskService service;
    /**
     * 新增应急任务
     */
    @PostMapping
    public ResultEntity createTask(
            @Validated
            @NotNull(message = "TaskVo不能为null")
            @RequestBody TaskVo taskVo, Principal principal){
        service.create(taskVo,principal);
        return ResultUtils.success();
    }


    /**
     * 修改应急任务
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateTask(
            @Validated
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            @NotNull(message = "TaskVo不能为null")
            @RequestBody TaskVo taskVo, Principal principal){
        service.update(taskVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条应急任务信息
     */
   @GetMapping(path = "/{id}")
    public ResultEntity findTaskById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
       TaskVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条应急任务信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteTask(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }
    /**
     * 查询应急任务列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(
            @RequestBody TaskPageVo taskPageVo,
            Principal principal){
        RestPageImpl<TaskVo> pageVo = service.findPage(taskPageVo,principal);
        return ResultUtils.success(pageVo);
    }
    /**
     * 查询应急任务列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(
            @RequestBody TaskPageVo taskPageVo,Principal principal){
        List<TaskVo> listVo = service.findList(taskPageVo,principal);
        return ResultUtils.success(listVo);
    }

    /**
     * 下发任务信息
     */
    @PostMapping(path = "/dispatch")
    public  ResultEntity dispatchTask(
            @NotNull(message = "DispatchVo不能为null")
            @RequestBody DispatchVo dispatchVo,Principal principal){
        service.dispatch(dispatchVo,principal);
        return ResultUtils.success();
    }

    /**
     * 根据预案Ids生成任务信息
     * @param buildTaskVo
     * @param principal
     * @return
     */
    @PostMapping(path = "/plans")
    public ResultEntity buildTask(
            @NotNull(message = "TaskVo不能为null")
            @RequestBody BuildTaskVo buildTaskVo, Principal principal){
        service.buildTask(buildTaskVo,principal);
        return ResultUtils.success();
    }

    /**
     * 任务时间轴
     * @param timeAxisTaskVo
     * @return
     */
    @PostMapping(path = "/timeAxis")
    public ResultEntity timeAxisTask(
            @NotNull(message = "TaskVo不能为null")
            @RequestBody TimeAxisTaskVo timeAxisTaskVo){
        List<EcTaskListVo> vo = service.timeAxisTask(timeAxisTaskVo);
        return ResultUtils.success(vo);
    }

    //任务总条数，及反馈信息总条数
    @PostMapping(path = "/taskStat")
    public ResultEntity taskStat(
            @NotNull(message = "TaskVo不能为null")
            @RequestBody TimeAxisTaskVo timeAxisTaskVo){
        Map<String,Integer> vo = service.taskStat(timeAxisTaskVo);
        return ResultUtils.success(vo);
    }

}
