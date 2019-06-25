package com.taiji.emp.event.eva.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.eva.entity.EventEvaScore;
import com.taiji.emp.event.eva.entity.QEventEvaScore;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class EventEvaScoreRepository extends BaseJpaRepository<EventEvaScore,String> {
    @Override
    @Transactional
    public EventEvaScore save(EventEvaScore entity) {
        Assert.notNull(entity,"entity对象不能为空");
        EventEvaScore result;
        if (null == entity.getId()){
            result = super.save(entity);
        }else {
            EventEvaScore temp = super.findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据reportId查询评估项目、报告关联信息
     * @param reportId
     * @return
     */
    public List<EventEvaScore> findByReportId(String reportId) {
        Assert.hasText(reportId,"reportId不能为空");
        QEventEvaScore eventEvaScore = QEventEvaScore.eventEvaScore;
        JPQLQuery<EventEvaScore> query = from(eventEvaScore);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(reportId)){
            builder.and(eventEvaScore.reportId.eq(reportId));
        }
        query.where(builder);
        return findAll(query);
    }
}
