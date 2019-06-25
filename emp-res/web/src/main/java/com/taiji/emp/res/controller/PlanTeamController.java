package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planTeam.PlanTeamListVo;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamPageVo;
import com.taiji.emp.res.service.PlanTeamService;
import com.taiji.emp.res.vo.PlanTeamVo;
import com.taiji.emp.res.vo.TeamVo;
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
@RequestMapping("/planTeams")
public class PlanTeamController extends BaseController {

    @Autowired
    PlanTeamService service;

    /**
     * 新增预案团队管理
     */
    @PostMapping
    public ResultEntity createPlanTeam(
            @Validated
            @NotNull(message = "PlanTeamVo不能为null")
            @RequestBody PlanTeamVo planTeamVo, Principal principal){
        service.create(planTeamVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案团队管理
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanTeam(
            @NotNull(message = "PlanTeamVo不能为null")
            @RequestBody PlanTeamVo planTeamVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planTeamVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案团队管理信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanTeamById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanTeamVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据参数逻辑删除单条预案团队管理信息
     */
    @PostMapping(path = "/removeTeam")
    public ResultEntity deletePlanTeam(
            @Validated
            @NotNull(message = "PlanTeamVo不能为null")
            @RequestBody PlanTeamVo planTeamVo){
        service.deletePhysical(planTeamVo);
        return ResultUtils.success();
    }

    /**
     * 查询预案团队管理列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody PlanTeamPageVo planTeamPageVo){
        RestPageImpl<TeamVo> pageVo = service.findPage(planTeamPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询预案团队管理列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PlanTeamListVo planTeamListVo){
        List<TeamVo> listVo = service.findList(planTeamListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 查询预案团队管理列表 --- 不分页去重
     */
    @PostMapping(path = "/searchTeamsByPlanIds")
    public ResultEntity searchTeamsByPlanIds(@RequestBody PlanTeamListVo planTeamListVo){
        List<TeamVo> listVo = service.findTeamsByPlanIds(planTeamListVo);
        return ResultUtils.success(listVo);
    }
}
