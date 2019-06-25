package com.taiji.emp.event.infoConfig.controller;

import com.taiji.emp.event.infoConfig.service.AccInfoService;
import com.taiji.emp.event.infoConfig.service.AccRuleService;
import com.taiji.emp.event.infoConfig.vo.AcceptRuleVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/acceptRules")
public class AccRuleController extends BaseController{

    @Autowired
    AccRuleService service;

    /**
     * 新增接报要求AcceptRuleVo
     * @param vo
     */
    @PostMapping
    public ResultEntity addAcceptRule(
            @Validated
            @NotNull(message = "acceptRuleVo 不能为null")
            @RequestBody AcceptRuleVo vo){
        service.create(vo);
        return ResultUtils.success();
    }

    /**
     * 更新接报要求AcceptRuleVo
     * @param vo
     * @param id
     */
    @PutMapping("/{id}")
    public ResultEntity updateAcceptRule(
            @Validated
            @NotNull(message = "acceptRuleVo 不能为null")
            @RequestBody AcceptRuleVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        service.update(vo,id);
        return ResultUtils.success();
    }

    /**
     * 根据查询条件单个AcceptRuleVo
     * 参数keys：eventTypeId
     */
    @PostMapping(path = "/getRuleSetting")
    public ResultEntity findAcceptRules(@RequestBody Map<String,Object> paramsMap){
        AcceptRuleVo resultVo = service.getRuleSetting(paramsMap);
        return ResultUtils.success(resultVo);
    }

}
