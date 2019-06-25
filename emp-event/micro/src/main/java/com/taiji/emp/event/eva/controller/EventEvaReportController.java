package com.taiji.emp.event.eva.controller;

import com.taiji.emp.event.eva.entity.EventEvaReport;
import com.taiji.emp.event.eva.feign.IEventEvaReportRestService;
import com.taiji.emp.event.eva.mapper.EventEvaReportMapper;
import com.taiji.emp.event.eva.service.EventEvaReportService;
import com.taiji.emp.event.eva.vo.EventEvaReportVo;
import com.taiji.emp.event.infoConfig.controller.BaseController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/evaReport")
public class EventEvaReportController extends BaseController implements IEventEvaReportRestService {

    EventEvaReportService service;
    EventEvaReportMapper mapper;

    /**
     * 新增事件评估报告
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<EventEvaReportVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaReportVo vo) {
        EventEvaReport entity = mapper.voToEntity(vo);
        EventEvaReport result = service.create(entity);
        EventEvaReportVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 获取单条 事件评估报告信息（含各评估细项分数）
     * @param eventId
     * @return
     */
    @Override
    public ResponseEntity<EventEvaReportVo> findOne(
            @NotEmpty(message = "eventId不能为空")
            @PathVariable(value = "eventId") String eventId) {
        EventEvaReport entity = service.findOne(eventId);
        EventEvaReportVo resultVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 修改事件评估报告信息（含各评估细项分数），若不是暂存即直接评估完成需要更新事件处置状态
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<EventEvaReportVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody EventEvaReportVo vo) {
        EventEvaReport entity = mapper.voToEntity(vo);
        EventEvaReport result = service.update(entity);
        EventEvaReportVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }
}
