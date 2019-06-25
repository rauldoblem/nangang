package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.OrgResponService;
import com.taiji.emp.event.cmd.vo.CmdOrgVo;
import com.taiji.emp.event.cmd.vo.OrgResponVo;
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
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cmd/orgrespons")
public class OrgResponController {

    @Autowired
    private OrgResponService service;

    /**
     * 新增应急责任单位/人
     */
    @PostMapping
    public ResultEntity addEcOrgRespon(
            @Validated
            @NotNull(message = "OrgResponVo 不能为null")
            @RequestBody OrgResponVo vo,Principal principal){
        service.addEcOrgRespon(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 获取单条责任单位/人信息
     */
    @GetMapping(path ="/{id}")
    public ResultEntity findEcOrgResponById(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id")String id){
        OrgResponVo resultVo = service.findEcOrgResponById(id);
        return ResultUtils.success(resultVo);
    }

    /**
     * 修改责任单位/人
     */
    @PutMapping(path ="/{id}")
    public ResultEntity updateEcOrgRespon(
            @Validated
            @NotNull(message = "OrgResponVo 不能为null")
            @RequestBody OrgResponVo vo,
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id,Principal principal){
        service.updateEcOrgRespon(vo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 删除责任单位/人
     */
    @DeleteMapping(path ="/{id}")
    public ResultEntity deleteEcOrgRespon(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteEcOrgRespon(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询责任单位/人信息
     * key:emgOrgId
     */
    @PostMapping(path ="/searchAll")
    public ResultEntity findOrgResponsAll(@RequestBody Map<String,Object> map){
        List<OrgResponVo> resultVoList = service.findOrgResponsAll(map);
        return ResultUtils.success(resultVoList);
    }

    /**
     * 根据启动的预案数字化信息生成关联的责任单位/人信息
     */
    @PostMapping(path ="/plans")
    public ResultEntity addPlanEmgOrgRespons(
            @RequestBody  List<CmdOrgVo> orgList, Principal principal){
        if(null!=orgList&&orgList.size()>0){
            service.addPlanEmgResponses(orgList,principal);
        }
        return ResultUtils.success();
    }

}
