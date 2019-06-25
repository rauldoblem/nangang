package com.taiji.emp.event.cmd.controller;


import com.taiji.emp.event.cmd.entity.EventLog;
import com.taiji.emp.event.cmd.feign.IEventLogRestService;
import com.taiji.emp.event.cmd.mapper.EventLogMapper;
import com.taiji.emp.event.cmd.service.EventLogService;
import com.taiji.emp.event.cmd.vo.EventLogVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/eventLog")
public class EventLogController extends BaseController implements IEventLogRestService {

    @Autowired
    EventLogService service;
    @Autowired
    EventLogMapper mapper;

    /**
     * 新增事件应急日志
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<EventLogVo> addEventLog(@Validated @NotNull(message = "EventLogVo 不能为null")@RequestBody EventLogVo vo) {
        EventLog entity = mapper.voToEntity(vo);
        EventLog result = service.addEventLog(entity);
        EventLogVo eventLogVo = mapper.entityToVo(result);
        return ResponseEntity.ok(eventLogVo);
    }

    /**
     * 删除事件应急日志
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(@NotEmpty(message = "id不能为空字符串") @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 修改事件应急日志
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<EventLogVo> update(@Validated @NotNull(message = "EventLogVo 不能为null") @RequestBody EventLogVo vo, @NotEmpty(message = "id不能为空字符串") @PathVariable(value = "id") String id) {
        vo.setId(id);
        EventLog entity = mapper.voToEntity(vo);
        EventLog result = service.update(entity);
        EventLogVo eventLogVo = mapper.entityToVo(result);
        return ResponseEntity.ok(eventLogVo);
    }

    /**
     * 获取单条事件应急日志信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<EventLogVo> findOne(@NotEmpty(message = "id不能为空字符串") @PathVariable(value = "id") String id) {
        EventLog entity = service.findOne(id);
        EventLogVo eventLogVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(eventLogVo);
    }

    /**
     * 查询事件应急日志列表
     * @return
     */
    @Override
    public ResponseEntity<List<EventLogVo>> findList(@Validated @NotNull(message = "EventLogVo 不能为null") @RequestBody EventLogVo vo) {
        List<EventLog> resultList = service.findList(vo);
        List<EventLogVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
