package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.emp.res.service.PlanOrgResponService;
import com.taiji.emp.res.service.PlanOrgService;
import com.taiji.emp.res.vo.PlanOrgResponVo;
import com.taiji.emp.res.vo.PlanOrgVo;
import com.taiji.micro.common.entity.ResultEntity;
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
@RequestMapping("/planOrgRespons")
public class PlanOrgResponController extends BaseController {

    @Autowired
    PlanOrgResponService service;

    /**
     * 新增预案责任人、单位管理
     */
    @PostMapping
    public ResultEntity addPlanOrgRespon(
            @Validated
            @NotNull(message = "PlanOrgResponVo不能为null")
            @RequestBody PlanOrgResponVo planOrgResponVo, Principal principal){
        service.create(planOrgResponVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案责任人、单位管理
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanOrgRespon(
            @NotNull(message = "planOrgResponVo不能为null")
            @RequestBody PlanOrgResponVo planOrgResponVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planOrgResponVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案责任人、单位管理
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanOrgResponById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanOrgResponVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除预案责任人、单位管理
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deletePlanOrgRespon(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询预预案责任人、单位管理--- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findPlanOrgResponsAll(@RequestBody PlanOrgResponVo planOrgResponVo){
        List<PlanOrgResponVo> listVo = service.findList(planOrgResponVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 通过planIds查询预预案责任人、单位
     */
    @PostMapping(path = "/searchOrgResponsByPlanOrgIds")
    public ResultEntity findByPlanOrgIds(@RequestBody PlanOrgResponVo planOrgResponVo){
        List<PlanOrgResponVo> listVo = service.findByPlanOrgIds(planOrgResponVo);
        return ResultUtils.success(listVo);
    }
}
