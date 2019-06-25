package com.taiji.emp.event.eva.service;

import com.taiji.emp.event.eva.entity.EventEvaItem;
import com.taiji.emp.event.eva.entity.EventEvaScore;
import com.taiji.emp.event.eva.repository.EventEvaScoreRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventEvaScoreService extends BaseService<EventEvaScore,String> {

    @Autowired
    EventEvaScoreRepository repository;

    /**
     * 新增评估项目和关联表
     * @param list
     * @return
     */
    public List<EventEvaScore> create(List<EventEvaScore> list) {
        List<EventEvaScore> scoreList = setDelFlagData(list);
        return scoreList;
    }

    /**
     * 更新评估项目和关联表
     * @param list
     * @return
     */
    public List<EventEvaScore> update(List<EventEvaScore> list) {
        List<EventEvaScore> scoreList = setDelFlagData(list);
        return scoreList;
    }

    private List<EventEvaScore> setDelFlagData(List<EventEvaScore> list){
        Assert.notNull(list,"list对象不能为空");
        List<EventEvaScore> scoreList = new ArrayList<>(list.size());
        for (EventEvaScore entity : list){
            EventEvaItem eventEvaItem = entity.getEventEvaItem();
            eventEvaItem.setDelFlag(DelFlagEnum.NORMAL.getCode());
            EventEvaScore eventEvaScore = repository.save(entity);
            scoreList.add(eventEvaScore);
        }
        return scoreList;
    }

    /**
     * 根据reportId查询评估项目、报告关联信息
     * @param reportId
     * @return
     */
    public List<EventEvaScore> findByReportId(String reportId) {
        Assert.hasText(reportId,"reportId不能为空");
        return repository.findByReportId(reportId);
    }
}
