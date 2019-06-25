package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.service.AnalyseService;
import com.taiji.emp.event.cmd.vo.AnalyseSaveVo;
import com.taiji.emp.event.cmd.vo.AnalyseVo;
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
@RequestMapping("/cmd/analyses")
public class AnalyseController {

    @Autowired
    private AnalyseService service;

    /**
     * 新增事件研判信息
     * @param vo
     */
    @PostMapping
    public ResultEntity addEventAnalyse(
            @Validated
            @NotNull(message = "AnalyseSaveVo 不能为null")
            @RequestBody AnalyseSaveVo vo, Principal principal){
        service.addEventAnalyse(vo,principal);
        return ResultUtils.success();
    }

    /**
     * 更新事件研判信息(含附件)
     * @param vo
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateEventAnalyse(
            @Validated
            @NotNull(message = "AnalyseSaveVo 不能为null")
            @RequestBody AnalyseSaveVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id, Principal principal){
        service.updateEventAnalyse(vo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 获取单条事件分析研判信息
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findEventAnalyseById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        AnalyseVo resultVo = service.findEventAnalyseById(id);
        return ResultUtils.success(resultVo);
    }

    /**
     * 删除单条事件分析研判信息(逻辑删除)
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteEventAnalyse(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        service.deleteEventAnalyse(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询事件分析研判结果列表-不分页
     * @param paramsMap
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findEventAnalysesAll(
            @RequestBody Map<String,Object> paramsMap){
        List<AnalyseVo> resultVo = service.findEventAnalysesAll(paramsMap);
        return ResultUtils.success(resultVo);
    }

}
