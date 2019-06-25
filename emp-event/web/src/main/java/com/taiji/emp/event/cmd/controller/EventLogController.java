package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.EventLogService;
import com.taiji.emp.event.cmd.vo.EventLogVo;
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

/**
 * 应急日志
 */
@Slf4j
@RestController
@RequestMapping("/cmd/eventLog")
public class EventLogController {

    @Autowired
    EventLogService service;

    /**
     * 新增事件应急日志
     * @param vo
     */
    @PostMapping
    public ResultEntity addEventLog(
            @Validated
            @NotNull(message = "vo 不能为null")
            @RequestBody EventLogVo vo, Principal principal){
        service.addEventLog(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 删除事件应急日志
     */
    @DeleteMapping(path ="/{id}")
    public ResultEntity deleteEventLog(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteEventLog(id);
        return ResultUtils.success();
    }

    /**
     * 修改事件应急日志
     */
    @PutMapping(path ="/{id}")
    public ResultEntity updateEventLog(
            @Validated
            @NotNull(message = "EventLogVo 不能为null")
            @RequestBody EventLogVo vo,
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id, Principal principal){
        service.updateEventLog(vo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 获取单条事件应急日志信息
     */
    @GetMapping(path ="/{id}")
    public ResultEntity findEventLogById(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id){
        EventLogVo resultVo = service.findEventLogById(id);
        return ResultUtils.success(resultVo);
    }

    /**
     * 查询事件应急日志列表 --- 不分页
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findList(@Validated @NotNull(message = "EventLogVo 不能为null") @RequestBody EventLogVo vo){
        List<EventLogVo> listVo = service.findList(vo);
        return ResultUtils.success(listVo);
    }
}
