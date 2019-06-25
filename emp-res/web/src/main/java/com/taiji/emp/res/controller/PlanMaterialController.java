package com.taiji.emp.res.controller;

import com.taiji.emp.res.searchVo.planMaterial.PlanMaterialListVo;
import com.taiji.emp.res.service.PlanMaterialService;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.res.vo.PlanMaterialSaveVo;
import com.taiji.emp.res.vo.PlanMaterialVo;
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
@RequestMapping("/planMaterials")
public class PlanMaterialController extends BaseController {

    @Autowired
    PlanMaterialService service;

    /**
     * 新增预案物资管理
     */
    @PostMapping
    public ResultEntity createPlanMaterial(
            @Validated
            @NotNull(message = "PlanMaterialVo不能为null")
            @RequestBody PlanMaterialSaveVo planMaterialSaveVo, Principal principal){
        service.create(planMaterialSaveVo,principal);
        return ResultUtils.success();
    }

    /**
     * 修改预案物资管理
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanMaterial(
            @NotNull(message = "PlanMaterialVo不能为null")
            @RequestBody PlanMaterialVo planMaterialVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planMaterialVo,principal,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案物资管理信息
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanMaterialById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanMaterialVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据参数逻辑删除单条预案物资管理信息
     */
    @PostMapping(path = "/removeExpert")
    public ResultEntity deletePlanMaterial(
            @Validated
            @NotNull(message = "PlanMaterialVo不能为null")
            @RequestBody PlanMaterialVo planMaterialVo){
        service.deletePhysical(planMaterialVo);
        return ResultUtils.success();
    }

    /**
     * 查询预案物资管理列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@RequestBody PlanMaterialListVo planMaterialListVo){
        List<PlanMaterialVo> listVo = service.findList(planMaterialListVo);
        return ResultUtils.success(listVo);
    }

    /**
     * 查询预案物资管理列表 --- 不分页去重
     */
    @PostMapping(path = "/searchMaterialsByPlanIds")
    public ResultEntity searchMaterialsByPlanIds(@RequestBody PlanMaterialListVo planMaterialListVo){
        List<MaterialVo> listVo = service.findMaterialsByPlanIds(planMaterialListVo);
        return ResultUtils.success(listVo);
    }
}
