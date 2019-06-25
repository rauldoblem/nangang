package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.CmdOrgService;
import com.taiji.emp.event.cmd.vo.CmdOrgVo;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
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
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cmd/emgorgs")
public class CmdOrgController {

    @Autowired
    private CmdOrgService service;

    /**
     * 新增应急组织机构
     */
    @PostMapping
    public ResultEntity addEcEmgOrg(
            @Validated
            @NotNull(message = "CmdOrgVo 不能为null")
            @RequestBody CmdOrgVo vo, Principal principal){
        service.addEcEmgOrg(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 获取单条应急组织机构信息
     */
    @GetMapping(path ="/{id}")
    public ResultEntity findEcEmgOrgById(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id){
        CmdOrgVo resultVo = service.findEcEmgOrgById(id);
        return ResultUtils.success(resultVo);
    }

    /**
     * 修改应急组织机构
     */
    @PutMapping(path ="/{id}")
    public ResultEntity updateEcEmgOrg(
            @Validated
            @NotNull(message = "CmdOrgVo 不能为null")
            @RequestBody CmdOrgVo vo,
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id, Principal principal){
        service.updateEcEmgOrg(vo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 删除应急组织机构（含该机构下关联的责任单位/人员）
     */
    @DeleteMapping(path ="/{id}")
    public ResultEntity deleteEcEmgOrg(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteEcEmgOrg(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询应急组织机构树信息
     * @param map
     * 参数key:schemeId,planId
     */
    @PostMapping(path ="/searchAll")
    public ResultEntity findEmgOrgsAll(@RequestBody Map<String,Object> map){
        if(map.containsKey("schemeId")&&map.containsKey("planId")){

            List<CmdOrgVo> resultVoList = service.findEmgOrgsAll(map);
            CmdOrgVo topVo = new CmdOrgVo();
            topVo.setId("1");
            topVo.setParentId("1");
            topVo.setName("应急组织");
            topVo.setLeaf("false");
            topVo.setOrders(1);
            resultVoList.add(topVo);
            List<CmdOrgVo> root = AssemblyTreeUtils.assemblyTree(resultVoList); //组装成树形

            return ResultUtils.success(root);

        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 根据启动的预案数字化信息生成关联的应急组织机构
     */
    @PostMapping(path ="/plans")
    public ResultEntity  addPlanEmgOrgs(
            @NotNull(message = "CmdPlansVo 不能为null")
            @RequestBody CmdPlansVo vo, Principal principal){
        List<CmdOrgVo> resultList = service.addPlanEmgOrgs(vo,principal);
        return ResultUtils.success(resultList);
    }

}
