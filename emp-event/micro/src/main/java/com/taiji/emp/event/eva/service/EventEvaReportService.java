package com.taiji.emp.event.eva.service;

import com.taiji.emp.event.eva.entity.EventEvaReport;
import com.taiji.emp.event.eva.repository.EventEvaReportRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@AllArgsConstructor
public class EventEvaReportService extends BaseService<EventEvaReport,String> {

    @Autowired
    private EventEvaReportRepository repository;

    /**
     * 新增事件评估报告
     * @param entity
     * @return
     */
    public EventEvaReport create(EventEvaReport entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        EventEvaReport result = repository.save(entity);
        return result;
    }

    /**
     * 获取单条 事件评估报告信息（含各评估细项分数）
     * @param eventId
     * @return
     */
    public EventEvaReport findOne(String eventId) {
        Assert.hasText(eventId,"eventId不能为null或空字符串");
        EventEvaReport evaReport = repository.findByEventId(eventId);
        return evaReport;
    }

    /**
     * 修改事件评估报告信息（含各评估细项分数），若不是暂存即直接评估完成需要更新事件处置状态
     * @param entity
     * @return
     */
    public EventEvaReport update(EventEvaReport entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        EventEvaReport eventEvaReport = repository.save(entity);
        return eventEvaReport;
    }
}
