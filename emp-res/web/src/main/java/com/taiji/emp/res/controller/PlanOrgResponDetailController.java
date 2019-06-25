package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
import com.taiji.emp.res.service.PlanOrgResponDetailService;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
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
@RequestMapping("/planOrgResponDetails")
public class PlanOrgResponDetailController extends BaseController {

    @Autowired
    PlanOrgResponDetailService service;

    /**
     * 新增预案责任人、单位详情管理
     */
    @PostMapping
    public ResultEntity addPlanOrgResponDetail(
            @Validated
            @NotNull(message = "PlanOrgResponDetailListVo不能为null")
            @RequestBody PlanOrgResponDetailListVo planOrgResponDetailListVo, Principal principal){
        service.create(planOrgResponDetailListVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案责任人、单位详情管理
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanOrgResponDetail(
            @NotNull(message = "PlanOrgResponDetailListVo不能为null")
            @RequestBody PlanOrgResponDetailListVo planOrgResponDetailListVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id,
            Principal principal){
        service.update(planOrgResponDetailListVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案责任人、单位详情管理
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanOrgResponDetailById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanOrgResponDetailVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除预案责任人、单位详情管理
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deletePlanOrgResponDetail(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询预预案责任人、单位详情管理--- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findPlanOrgResponDetailsAll(@RequestBody PlanOrgResponDetailVo planOrgResponDetailVo){
        List<PlanOrgResponDetailVo> listVo = service.findList(planOrgResponDetailVo);
        return ResultUtils.success(listVo);
    }
}
