package com.taiji.emp.event.infoConfig.controller;

import com.taiji.emp.event.infoConfig.service.AccInfoService;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/acceptInforms")
public class AccInfoController extends BaseController{

    @Autowired
    AccInfoService service;

    /**
     * 新增通知单位AcceptInformVo
     * @param vo
     */
    @PostMapping
    public ResultEntity addAcceptInform(
            @Validated
            @NotNull(message = "acceptInformVo 不能为null")
            @RequestBody AcceptInformVo vo){
        service.create(vo);
        return ResultUtils.success();
    }

    /**
     * 根据id 通知单位AcceptInformVo
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findAcceptInformById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        AcceptInformVo result = service.findOne(id);
        return ResultUtils.success(result);
    }

    /**
     * 修改通知单位AcceptInformVo
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateAcceptInform(
            @Validated
            @NotNull(message = "acceptInformVo 不能为null")
            @RequestBody AcceptInformVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        service.update(vo,id);
        return ResultUtils.success();
    }

    /**
     * 根据id删除单条通知单位信息
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteAcceptInform(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
        service.delete(id);
        return ResultUtils.success();
    }

    /**
     * 根据查询条件查询AcceptInformVo list
     * 参数keys：eventTypeId
     */
    @PostMapping(path="/searchAll")
    public ResultEntity findAcceptInforms(@RequestBody Map<String,Object> paramsMap){
        List<AcceptInformVo> resultList = service.searchAll(paramsMap);
        return ResultUtils.success(resultList);
    }

}
