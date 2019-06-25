package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planTask.PlanTaskListVo;
import com.taiji.emp.res.searchVo.planTask.PlanTaskPageVo;
import com.taiji.emp.res.service.PlanTaskService;
import com.taiji.emp.res.vo.PlanTaskVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/planTasks")
public class PlanTaskController extends BaseController {

    @Autowired
    PlanTaskService service;

    /**
     * 新增预案任务设置管理
     */
    @PostMapping
    public ResultEntity createPlanTask(
            @Validated
            @NotNull(message = "PlanTaskVo不能为null")
            @RequestBody PlanTaskVo planTaskVo, Principal principal){
        service.create(planTaskVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案任务设置管理信息
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanTask(
            @NotNull(message = "PlanTaskVo不能为null")
            @RequestBody PlanTaskVo planTaskVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planTaskVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案任务设置管理信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanTaskById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanTaskVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条预案任务设置管理信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deletePlanTask(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询预案任务设置管理列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody PlanTaskPageVo planTaskPageVo){
        RestPageImpl<PlanTaskVo> pageVo = service.findPage(planTaskPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询预案任务设置管理列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PlanTaskListVo planTaskListVo){
        List<PlanTaskVo> listVo = service.findList(planTaskListVo);
        return ResultUtils.success(listVo);
    }
}
