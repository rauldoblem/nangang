package com.taiji.emp.event.eva.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.eva.entity.EventEvaReport;
import com.taiji.emp.event.eva.entity.QEventEvaReport;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Repository
@Transactional(readOnly = true)
public class EventEvaReportRepository extends BaseJpaRepository<EventEvaReport,String> {

    /**
     * 新增或修改
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public EventEvaReport save(EventEvaReport entity){
        Assert.notNull(entity,"entity对象不能为空");
        EventEvaReport result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            EventEvaReport temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }


    public EventEvaReport findByEventId(String eventId) {
        Assert.hasText(eventId,"eventId不能为空");
        QEventEvaReport eventEvaReport = QEventEvaReport.eventEvaReport;
        JPQLQuery<EventEvaReport> query = from(eventEvaReport);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(eventId)){
            builder.and(eventEvaReport.eventId.eq(eventId));
        }
        builder.and(eventEvaReport.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        return query.where(builder).fetchOne();
    }
}
