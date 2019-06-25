package com.taiji.emp.event.eva.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.eva.entity.EventEvaProcess;
import com.taiji.emp.event.eva.entity.QEventEvaProcess;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class EventEvaProcessRepository extends BaseJpaRepository<EventEvaProcess,String> {


    //不带分页信息，查询过程再现列表
    public List<EventEvaProcess> findList(String eventId){
        JPQLQuery<EventEvaProcess> query = buildQuery(eventId);
        return findAll(query);
    }

    private JPQLQuery<EventEvaProcess> buildQuery(String eventId){
        QEventEvaProcess process = QEventEvaProcess.eventEvaProcess;
        JPQLQuery<EventEvaProcess> query = from(process);

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(process.eventId.eq(eventId));

        builder.and(process.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(EventEvaProcess.class
                        ,process.id
                        ,process.eventId
                        ,process.eventName
                        ,process.operation
                        ,process.operator
                        ,process.operationTime
                        ,process.firstNodeCode
                        ,process.firstNodeName
                        ,process.secNodeCode
                        ,process.secNodeName
                        ,process.processType
                )).where(builder)
                .orderBy(process.updateTime.asc());
        return query;
    }

    @Override
    @Transactional
    public EventEvaProcess save(EventEvaProcess entity){
        Assert.notNull(entity,"Contact 对象不能为 null");

        EventEvaProcess result;
        if(null == entity.getId()){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            EventEvaProcess temp = findOne(entity.getId());
                BeanUtils.copyNonNullProperties(entity, temp);
                result = super.save(temp);
        }
        return result;
    }
}
