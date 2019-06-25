package com.taiji.emp.event.cmd.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.EventLog;
import com.taiji.emp.event.cmd.entity.QEventLog;
import com.taiji.emp.event.cmd.vo.EventLogVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class EventLogRepository extends BaseJpaRepository<EventLog,String> {

    /**
     * 新增或编辑
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public EventLog save(EventLog entity) {
        Assert.notNull(entity,"EventLog对象不能为null");
        EventLog result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);  //新增
        }else {
            EventLog temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 查询事件应急日志列表
     * @return
     */
    public List<EventLog> findList(EventLogVo vo) {
        JPQLQuery<EventLog> query = buildQuery(vo);
        return findAll(query);
    }

    private JPQLQuery<EventLog> buildQuery(EventLogVo vo) {
        QEventLog eventLog = QEventLog.eventLog;
        BooleanBuilder builder = new BooleanBuilder();
        String eventId = vo.getEventId();
        if (StringUtils.hasText(eventId)){
            builder.and(eventLog.eventId.eq(eventId));
        }
        builder.and(eventLog.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        JPQLQuery<EventLog> query = from(eventLog);
        query.where(builder).orderBy(eventLog.updateTime.desc());
        return query;
    }
}
