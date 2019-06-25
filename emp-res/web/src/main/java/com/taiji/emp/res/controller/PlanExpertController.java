package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertPageVo;
import com.taiji.emp.res.service.PlanExpertService;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.emp.res.vo.PlanExpertVo;
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
@RequestMapping("/planExperts")
public class PlanExpertController extends BaseController {

    @Autowired
    PlanExpertService service;

    /**
     * 新增预案专家管理
     */
    @PostMapping
    public ResultEntity createPlanExpert(
            @Validated
            @NotNull(message = "PlanExpertVo不能为null")
            @RequestBody PlanExpertVo planExpertVo, Principal principal){
        service.create(planExpertVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案专家管理
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanExpert(
            @NotNull(message = "PlanExpertVo不能为null")
            @RequestBody PlanExpertVo planExpertVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planExpertVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案专家管理信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanExpertById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanExpertVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据参数逻辑删除单条预案专家管理信息
     */
    @PostMapping(path = "/removeExpert")
    public ResultEntity deletePlanExpert(
            @Validated
            @NotNull(message = "PlanExpertVo不能为null")
            @RequestBody PlanExpertVo planExpertVo){
        service.deletePhysical(planExpertVo);
        return ResultUtils.success();
    }

    /**
     * 查询预案专家管理列表 --- 分页
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(@RequestBody PlanExpertPageVo planExpertPageVo){
        RestPageImpl<ExpertVo> pageVo = service.findPage(planExpertPageVo);
        return ResultUtils.success(pageVo);
    }

    /**
     * 查询预案专家管理列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PlanExpertListVo planExpertListVo){
        List<ExpertVo> listVo = service.findList(planExpertListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 查询预案专家管理列表 --- 不分页去重
     */
    @PostMapping(path = "/searchExpertsByPlanIds")
    public ResultEntity findExpertsByPlanIds(@RequestBody PlanExpertListVo planExpertListVo){
        List<ExpertVo> listVo = service.findExpertsByPlanIds(planExpertListVo);
        return ResultUtils.success(listVo);
    }
}
