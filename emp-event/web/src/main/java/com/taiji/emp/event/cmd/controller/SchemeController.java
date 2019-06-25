package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.SchemeService;
import com.taiji.emp.event.cmd.vo.SchemeVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cmd/schemes")
public class SchemeController {

    @Autowired
    private SchemeService service;

    /**
     * 启动预案，生成处置方案基本信息，处置方案名称默认为‘事件名称+处置方案
     * @param map
     * 包含事件信息
     */
    @PostMapping
    public ResultEntity addEcScheme(
            @NotNull(message = "map不能为null")
            @RequestBody Map<String,Object> map, Principal principal){
        if(map.containsKey("eventId") && map.containsKey("eventName")){
            SchemeVo resultVo = service.addEcScheme(map,principal);
            return ResultUtils.success(resultVo);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }

    }

    /**
     * 根据事件ID获取事件处置方案基本信息
     * @param eventId 事件id
     */
    @GetMapping
    public ResultEntity findSchemeVoByEventId(
            @NotEmpty(message = "eventId 不能为空字符串")
            @RequestParam(value = "eventId") String eventId){
        SchemeVo resultVo = service.findSchemeVoByEventId(eventId);
        return ResultUtils.success(resultVo);
    }

    /**
     * 修改处置方案
     * @param id 待更新的方案id
     * @param vo 待更新的方案信息
     * @param principal
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateScheme(
            @Validated
            @NotNull(message = "SchemeVo 不能为null")
            @RequestBody SchemeVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id")String id, Principal principal){
        SchemeVo resultVo = service.updateScheme(vo,id,principal);
        return ResultUtils.success(resultVo);
    }

}
