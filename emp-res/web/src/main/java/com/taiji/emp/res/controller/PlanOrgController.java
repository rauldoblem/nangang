package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.emp.res.service.PlanOrgService;
import com.taiji.emp.res.vo.PlanOrgVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
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
@RequestMapping("/planOrgs")
public class PlanOrgController extends BaseController {

    @Autowired
    PlanOrgService service;

    /**
     * 新增预案组织机构管理
     */
    @PostMapping
    public ResultEntity addPlanOrg(
            @Validated
            @NotNull(message = "PlanOrgVo不能为null")
            @RequestBody PlanOrgVo planOrgVo, Principal principal){
        service.create(planOrgVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案组织机构管理
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanOrg(
            @NotNull(message = "SupportVo不能为null")
            @RequestBody PlanOrgVo planOrgVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planOrgVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案组织机构管理
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanOrgById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanOrgVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除预案组织机构管理
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deletePlanOrg(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询预案组织机构管理--- 不分页树形结构
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findPlanOrgsAll(@RequestBody PlanOrgListVo planOrgListVo){
        List<PlanOrgVo> listVo = service.findList(planOrgListVo);
        PlanOrgVo planOrgVo = new PlanOrgVo();
        planOrgVo.setId("1");
        planOrgVo.setParentId("1");
        planOrgVo.setName("应急组织");
        planOrgVo.setLeaf("false");
        planOrgVo.setOrders(1);
        listVo.add(planOrgVo);
        List<PlanOrgVo> root = AssemblyTreeUtils.assemblyTree(listVo);
        return ResultUtils.success(root);
    }

    /**
     * 查询预案组织机构管理--- 不分页树形结构
     */
    @PostMapping(path = "/searchAllByPlanIds")
    public ResultEntity findByPlanIds(@RequestBody PlanOrgListVo planOrgListVo){
        List<PlanOrgVo> listVo = service.findByPlanIds(planOrgListVo);
        PlanOrgVo planOrgVo = new PlanOrgVo();
        planOrgVo.setId("1");
        planOrgVo.setParentId("-1");
        planOrgVo.setName("应急组织");
        planOrgVo.setLeaf("0");
        planOrgVo.setOrders(1);
        listVo.add(planOrgVo);
        List<PlanOrgVo> root = AssemblyTreeUtils.assemblyTree(listVo);
        return ResultUtils.success(root);
    }
}
