package com.taiji.emp.base.controller;

import com.taiji.emp.base.service.ConditionSettingService;
import com.taiji.emp.base.vo.ConditionSettingVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/29 15:17
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/conditionSet")
public class ConditionSettingController {

    @Autowired
    private ConditionSettingService service;

    /**
     * 新增事件类型对应不同等级的应急响应启动条件（list）
     * @param conditionSettingVos
     * @return
     */
    @PostMapping
    public ResultEntity create(
            @Validated
            @NotNull(message = "conditionSettingVo不能为null")
            @RequestBody List<ConditionSettingVo> conditionSettingVos){
        service.create(conditionSettingVos);
        return ResultUtils.success();
    }

    /**
     * 根据事件类型Id，获取该事件类型不同等级的应急响应条件列表
     * @param eventTypeId
     * @return
     */
    @GetMapping("/{eventTypeId}")
    public ResultEntity findList(
            @Validated
            @NotNull(message = "eventTypeId不能为空")
            @PathVariable(name = "eventTypeId") String eventTypeId){

        List<ConditionSettingVo> results = service.findList(eventTypeId);
        return ResultUtils.success(results);
    }
}
