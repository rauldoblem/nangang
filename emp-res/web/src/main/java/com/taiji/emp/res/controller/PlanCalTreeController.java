package com.taiji.emp.res.controller;

import com.taiji.emp.res.service.PlanCalTreeService;
import com.taiji.emp.res.vo.PlanCalTreeVo;
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
@RequestMapping("/planCaltrees")
public class PlanCalTreeController extends BaseController {

    @Autowired
    private PlanCalTreeService service;

    /**
     * 新增预案目录
     * @param planCalTreeVo
     * @return
     */
    @PostMapping
    public ResultEntity createPlanCalTree(
            @Validated
            @NotNull(message = "PlanBaseVo不能为null")
            @RequestBody PlanCalTreeVo planCalTreeVo){
        service.create(planCalTreeVo);
        return ResultUtils.success();
    }

    /**
     * 修改预案目录
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updatePlanCalTree(
            @NotNull(message = "PlanCalTreeVo不能为null")
            @RequestBody PlanCalTreeVo planCalTreeVo,
            @PathVariable(name = "id") String id,
            Principal principal){
        service.update(planCalTreeVo,id);
        return ResultUtils.success();
    }

    /**
     * 根据id获取单条预案目录
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findPlanCalTreeById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        PlanCalTreeVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    /**
     * 根据id逻辑删除预案目录
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deletePlanCalTree(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 查询预案目录--- 不分页树形结构
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findPlanCalTreeAll(){
        List<PlanCalTreeVo> listVo = service.findList();
        PlanCalTreeVo planCalTreeVo = new PlanCalTreeVo();
        planCalTreeVo.setId("1");
        planCalTreeVo.setParentId("1");
        planCalTreeVo.setCalName("预案目录");
        planCalTreeVo.setLeaf("false");
        planCalTreeVo.setOrders(1);
        listVo.add(planCalTreeVo);
        List<PlanCalTreeVo> root = AssemblyTreeUtils.assemblyTree(listVo);
        return ResultUtils.success(root);
    }

}
