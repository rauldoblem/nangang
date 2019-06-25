package com.taiji.emp.screenShow.controller;

import com.taiji.emp.event.common.enums.RepStatusEnum;
import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.emp.event.infoDispatch.vo.AcceptIsFirstOrNotFirst;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import com.taiji.emp.screenShow.service.EventService;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

    EventService service;

    /**
     * 根据条件查询事件列表-不分页
     *
     * @param pageVo
     * @return ResultEntity<List < EventVo>>
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity<List<EventVo>> findEvents(
            @Validated
            @NotNull(message = "EventPageVo 不能为null")
            @RequestBody EventPageVo pageVo){
        List<EventVo> resultVo = service.findList(pageVo);
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
        AcceptIsFirstOrNotFirst resultVo = service.findAccListByEventId(eventId);
        return ResultUtils.success(resultVo);
    }

}
