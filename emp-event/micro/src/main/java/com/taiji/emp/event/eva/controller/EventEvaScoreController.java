package com.taiji.emp.event.eva.controller;

import com.taiji.emp.event.eva.entity.EventEvaScore;
import com.taiji.emp.event.eva.feign.IEventEvaScoreRestService;
import com.taiji.emp.event.eva.mapper.EventEvaScoreMapper;
import com.taiji.emp.event.eva.service.EventEvaScoreService;
import com.taiji.emp.event.eva.vo.EventEvaScoreVo;
import com.taiji.emp.event.infoConfig.controller.BaseController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/evaScore")
public class EventEvaScoreController extends BaseController implements IEventEvaScoreRestService {

    @Autowired
    EventEvaScoreService service;

    @Autowired
    EventEvaScoreMapper mapper;

    /**
     * 新增评估项目和关联表
     * @param evaScoreList
     * @return
     */
    @Override
    public ResponseEntity<List<EventEvaScoreVo>> create(
            @NotNull(message = "evaScoreList不能为null")
            @RequestBody List<EventEvaScoreVo> evaScoreList) {
        List<EventEvaScore> list = mapper.voListToEntityList(evaScoreList);
        List<EventEvaScore> result = service.create(list);
        List<EventEvaScoreVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 更新评估项目和关联表
     * @param evaScoreList
     * @return
     */
    @Override
    public ResponseEntity<List<EventEvaScoreVo>> update(
            @NotNull(message = "evaScoreList不能为null")
            @RequestBody List<EventEvaScoreVo> evaScoreList) {
        List<EventEvaScore> list = mapper.voListToEntityList(evaScoreList);
        List<EventEvaScore> result = service.update(list);
        List<EventEvaScoreVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据reportId查询评估项目、报告关联信息
     * @param reportId
     * @return
     */
    @Override
    public ResponseEntity<List<EventEvaScoreVo>> findByReportId(
            @NotEmpty(message = "reportId不能为空")
            @PathVariable(value = "reportId") String reportId) {
        List<EventEvaScore> list = service.findByReportId(reportId);
        List<EventEvaScoreVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
