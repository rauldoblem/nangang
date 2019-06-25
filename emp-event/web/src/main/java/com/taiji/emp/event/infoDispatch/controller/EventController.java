package com.taiji.emp.event.infoDispatch.controller;

import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.emp.event.infoDispatch.service.EventService;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;

    /**
     * 生成新事件
     * @param vo
     * @return ResultEntity<Void>
     */
    @PostMapping
    public ResultEntity<EventVo> addEvent(
            @Validated
            @NotNull(message = "EventVo 不能为null")
            @RequestBody EventVo vo, Principal principal){
        EventVo resultVo = service.create(vo,principal);
        return ResultUtils.success(resultVo);
    }

    /**
     *根据id获取单个事件信息
     * @param id
     * @return ResultEntity<EventVo>
     */
    @GetMapping(path = "/{id}")
    public ResultEntity<EventVo> findEventById(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id){
        EventVo resultVo = service.findOne(id);
        return ResultUtils.success(resultVo);
    }

    /**
     * 更新事件
     *
     * @param vo
     * @param id 事件id
     * @return ResultEntity<Void>
     * */
    @PutMapping(path = "/{id}")
    public ResultEntity<EventVo> updateEvent(
            @Validated
            @NotNull(message = "EventVo 不能为null")
            @RequestBody EventVo vo,
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id,
            Principal principal){
        EventVo resultVo =service.update(vo,id,principal);
        return ResultUtils.success(resultVo);
    }

    /**
     * 根据条件查询事件列表-分页
     *
     * @param pageVo
     * @return ResultEntity<RestPageImpl < EventVo>>
     */
    @PostMapping(path = "/search")
    public ResultEntity<RestPageImpl<EventVo>> findEvents(
            @Validated
            @NotNull(message = "EventPageVo 不能为null")
            @RequestBody EventPageVo pageVo){
        RestPageImpl<EventVo> resultVo = service.findPage(pageVo);
        return ResultUtils.success(resultVo);
    }

    /**
     * 事件处置结束/评估/归档，将事件处置状态置为已结束/已评估/已归档状态
     *
     * @param eventId
     * @param operateStatus
     * 事件处置状态：1处置结束  2 已评估 3 已归档
     * @return ResultEntity<Void>
     */
    @PutMapping(path = "/updateStatus")
    public ResultEntity<Void> operateEvent(
            @NotEmpty(message = "eventId 不能为空")
            @RequestParam(value = "eventId") String eventId,
            @NotEmpty(message = "handleFlag 不能为空")
            @RequestParam(value = "handleFlag") String operateStatus,
            Principal principal){
        if(EventGlobal.eventHandleTypeSet.contains(operateStatus)){
            service.operateEvent(eventId,operateStatus,principal);
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"operateStatus 操作码传输异常");
        }
    }

    /**
     * 调整事件等级，与原事件等级比对，更新事件表中的事件等级
     * @param eventId
     * @param eventGradeId
     * @return ResultEntity<Void>
     */
    @PutMapping(path = "/adjustGrade")
    public ResultEntity<Void> adjustEventGrade(
            @NotEmpty(message = "eventId 不能为空")
            @RequestParam(value = "eventId") String eventId,
            @NotEmpty(message = "eventGradeId 不能为空")
            @RequestParam(value = "eventGradeId") String eventGradeId,
            Principal principal){
        boolean result = service.adjustEventGrade(eventId,eventGradeId,principal);
        if(result){
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"事件等级与原有等级一致");
        }
    }

    /**
     * 根据条件导出事件word
     * @param eventId
     */
    @GetMapping(path = "/exportToWord")
    public ResultEntity<EventVo> exportToWord(
            @NotEmpty(message = "eventId 不能为空")
            @RequestParam(value = "eventId") String eventId
            , HttpServletResponse response
            , HttpServletRequest request)throws IOException {
        service.exportToWord(response,eventId,request);
        return ResultUtils.success();
    }

}
