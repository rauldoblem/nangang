package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planBase.PlanBaseListVo;
import com.taiji.emp.res.searchVo.planBase.PlanBasePageVo;
import com.taiji.emp.res.service.PlanBaseService;
import com.taiji.emp.res.vo.PlanBaseSaveVo;
import com.taiji.emp.res.vo.PlanBaseVo;
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
@RequestMapping("/plans")
public class PlanBaseController extends BaseController {

    @Autowired
    PlanBaseService service;

    /**
     * 新增预案管理基础
     */
    @PostMapping
    public ResultEntity createPlanBase(
            @Validated
            @NotNull(message = "PlanBaseVo不能为null")
            @RequestBody PlanBaseSaveVo planBaseSaveVo, Principal principal){
        service.create(planBaseSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案管理基础
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanBase(
            @NotNull(message = "PlanBaseVo不能为null")
            @RequestBody PlanBaseSaveVo planBaseSaveVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planBaseSaveVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案管理基础信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanBaseById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanBaseVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除单条预案管理基础信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deletePlanBase(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询预案管理基础列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody PlanBasePageVo planBasePageVo){
        RestPageImpl<PlanBaseVo> pageVo = service.findPage(planBasePageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询预案管理基础列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PlanBaseListVo planBaseListVo){
        List<PlanBaseVo> listVo = service.findList(planBaseListVo);
        return ResultUtils.success(listVo);
    }
}
