package com.taiji.emp.event.infoDispatch.controller;

import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.infoDispatch.searchVo.InfoPageVo;
import com.taiji.emp.event.infoDispatch.service.AcceptService;
import com.taiji.emp.event.infoDispatch.vo.AccDealVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptSaveVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
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
@RequestMapping("/infoMags")
public class AcceptController {

    @Autowired
    private AcceptService service;


    /**
     * 根据参数获取AccDealVo多条记录,分页信息
     * 参数key为 buttonType,eventName,eventTypeIds(数组),eventGradeId,startDate,endDate
     * page,size
     * @param infoPageVo
     */
    @PostMapping(path = "/searchInfoByButton")
    public ResultEntity findInfoReports(
            @NotNull(message = "infoPageVo 不能为null")
            @RequestBody InfoPageVo infoPageVo){
        RestPageImpl<AccDealVo> resultVo = service.findPage(infoPageVo);
        return ResultUtils.success(resultVo);
    }

    /**
     * 信息填报
     * 包括初报、续报
     * @param acceptSaveVo
     */
    @PostMapping
    public ResultEntity addInfo(
            @Validated
            @NotNull(message = "acceptSaveVo 不能为null")
            @RequestBody AcceptSaveVo acceptSaveVo, Principal principal){
        AcceptVo acceptVo = service.addInfo(acceptSaveVo,principal);
        return ResultUtils.success(acceptVo);
    }

    /**
     * 修改信息 -- 只更新主表
     *
     * @param acceptSaveVo
     * @param id       信息id
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateInfo(
            @Validated
            @NotNull(message = "acceptSaveVo 不能为null")
            @RequestBody AcceptSaveVo acceptSaveVo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id, Principal principal){
        service.updateInfo(acceptSaveVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 获取单条上报、接报信息
     * @param id 信息id
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findInfoReportById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        AcceptVo resultVo = service.findOne(id);
        return ResultUtils.success(resultVo);
    }

    /**
     * 逻辑删除信息 -- 只删除主表
     *
     * @param id 信息id
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteInfo(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    /**
     * 办理信息，包括发送、退回、办结、生成/更新事件
     *
     * @param accDealVo  web需要从 accept id --->firstReportId --->eventId
     * @param buttonFlag - 发送1、退回2、办结3、生成事件4、更新事件5
     */
    @PostMapping(path = "/dealInfo")
    public ResultEntity dealInfo(
            @Validated
            @NotNull(message = "accDealVo 不能为空")
            @RequestBody AccDealVo accDealVo,
            @NotEmpty(message = "buttonFlag 不能为空字符串")
            @RequestParam(value = "buttonFlag") String buttonFlag,Principal principal){
        if(EventGlobal.buttonTypeSet.contains(buttonFlag)){
            service.dealInfo(accDealVo,buttonFlag,principal);
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 根据初报Id 获取已生成事件的 eventId
     * @param firstReportId 初报Id
     */
    @GetMapping(path = "/getEventIdByFirstInfoId/{id}")
    public ResultEntity getEventIdByInfoId(
            @NotEmpty(message = "初报Id不能为空字符串")
            @PathVariable(value = "id") String firstReportId){
        String eventId  = service.getEventIdByInfoId(firstReportId);
        return ResultUtils.success(eventId);
    }

    /**
     * 查看退回原因
     * @param acceptDealId ---报送处理信息Id
     */
    @GetMapping(path = "/checkReturnReason/{id}")
    public ResultEntity checkReturnReason(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String acceptDealId){
        AccDealVo resultVo = service.checkReturnReason(acceptDealId);
        return ResultUtils.success(resultVo);
    }

    /**
     * 根据eventId查询所有初报续报信息
     * @param eventId 生成事件Id
     */
    @PostMapping(path = "/searchInfoByEvent")
    public ResultEntity getInfoListByEventId(
            @NotEmpty(message = "eventId 不能为空字符串")
            @RequestParam(value = "eventId") String eventId){
        List<AcceptVo> resultVoList = service.findAccListByEventId(eventId);
        return ResultUtils.success(resultVoList);
    }

}
