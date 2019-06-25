package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planSupport.PlanSupportListVo;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportPageVo;
import com.taiji.emp.res.service.PlanSupportService;
import com.taiji.emp.res.vo.PlanSupportVo;
import com.taiji.emp.res.vo.SupportVo;
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
@RequestMapping("/planSupports")
public class PlanSupportController extends BaseController {

    @Autowired
    PlanSupportService service;

    /**
     * 新增预案社会依托资源管理
     */
    @PostMapping
    public ResultEntity createPlanSupport(
            @Validated
            @NotNull(message = "PlanSupportVo不能为null")
            @RequestBody PlanSupportVo planSupportVo, Principal principal){
        service.create(planSupportVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案社会依托资源管理
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanSupport(
            @NotNull(message = "PlanSupportVo不能为null")
            @RequestBody PlanSupportVo planSupportVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planSupportVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案社会依托资源管理信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanSupportById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanSupportVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据参数逻辑删除单条预案社会依托资源管理信息
     */
    @PostMapping(path = "/removeSupport")
    public ResultEntity deletePlanSupport(
            @Validated
            @NotNull(message = "PlanSupportVo不能为null")
            @RequestBody PlanSupportVo planSupportVo){
        service.deletePhysical(planSupportVo);
        return ResultUtils.success();
    }

    /**
     * 查询预案社会依托资源管理列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody PlanSupportPageVo planSupportPageVo){
        RestPageImpl<SupportVo> pageVo = service.findPage(planSupportPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询预案社会依托资源管理列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PlanSupportListVo planSupportListVo){
        List<SupportVo> listVo = service.findList(planSupportListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 查询预案社会依托资源管理列表 --- 不分页去重
     */
    @PostMapping(path = "/searchSupportsByPlanIds")
    public ResultEntity searchSupportsByPlanIds(@RequestBody PlanSupportListVo planSupportListVo){
        List<SupportVo> listVo = service.findSupportsByPlanIds(planSupportListVo);
        return ResultUtils.success(listVo);
    }
}
