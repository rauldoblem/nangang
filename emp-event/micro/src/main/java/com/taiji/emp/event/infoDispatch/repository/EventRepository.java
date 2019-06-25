package com.taiji.emp.event.infoDispatch.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.infoDispatch.entity.Event;
import com.taiji.emp.event.infoDispatch.entity.QEvent;
import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class EventRepository extends BaseJpaRepository<Event,String> {

    @Override
    @Transactional
    public Event save(Event entity){
        Assert.notNull(entity,"Event 不能为null");
        String id = entity.getId();
        Event result;
        if(StringUtils.isEmpty(id)){ //生成事件
            result = super.save(entity);
        }else{ //更新事件
            Event temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //根据条件查询事件列表-分页
    public Page<Event> findPage(EventPageVo eventPageVo, Pageable pageable){

        String eventName = eventPageVo.getEventName();
        String eventGradeId = eventPageVo.getEventGradeId();
        List<String> eventTypeIds = eventPageVo.getEventTypeIds();
        List<String> handleFlags = eventPageVo.getHandleFlags();
        String createOrgId = eventPageVo.getCreateOrgId();

        QEvent event = QEvent.event;
        JPQLQuery<Event> query = from(event);
        BooleanBuilder builder = new BooleanBuilder();

        if(!StringUtils.isEmpty(eventName)){
            builder.and(event.eventName.contains(eventName));
        }
        if(!StringUtils.isEmpty(eventGradeId)){
            builder.and(event.eventGradeId.eq(eventGradeId));
        }
        if(null!=eventTypeIds&&eventTypeIds.size()>0){
            builder.and(event.eventTypeId.in(eventTypeIds));
        }
        if(null!=handleFlags&&handleFlags.size()>0){
            builder.and(event.handleFlag.in(handleFlags));
        }
        if(!StringUtils.isEmpty(createOrgId)){
            builder.and(event.createOrgId.eq(createOrgId));
        }

        query.select(Projections.bean(
                Event.class
                ,event.id
                ,event.eventName
                ,event.position
//                ,event.lonAndLat
                ,event.occurTime
                ,event.eventTypeId
                ,event.eventTypeName
                ,event.eventGradeId
                ,event.eventGradeName
//                ,event.reportOrgId
//                ,event.reportOrgName
//                ,event.reporter
//                ,event.reporterTel
//                ,event.methodId
//                ,event.methodName
//                ,event.reportTime
//                ,event.eventCause
                ,event.eventDesc
//                ,event.protreatment
//                ,event.deathNum
//                ,event.wondedNum
//                ,event.request
                ,event.handleFlag
//                ,event.hBeginTime
//                ,event.hEndTime
            )).where(builder)
                .orderBy(event.createTime.desc()); //按照创建时间倒叙排

        return findAll(query,pageable);
    }

    //根据条件查询事件列表-不分页
    public List<Event> findList(EventPageVo eventPageVo){

        String eventName = eventPageVo.getEventName();
        String eventGradeId = eventPageVo.getEventGradeId();
        List<String> eventTypeIds = eventPageVo.getEventTypeIds();
        List<String> handleFlags = eventPageVo.getHandleFlags();
        String createOrgId = eventPageVo.getCreateOrgId();

        QEvent event = QEvent.event;
        JPQLQuery<Event> query = from(event);
        BooleanBuilder builder = new BooleanBuilder();

        if(!StringUtils.isEmpty(eventName)){
            builder.and(event.eventName.contains(eventName));
        }
        if(!StringUtils.isEmpty(eventGradeId)){
            builder.and(event.eventGradeId.eq(eventGradeId));
        }
        if(null!=eventTypeIds&&eventTypeIds.size()>0){
            builder.and(event.eventTypeId.in(eventTypeIds));
        }
        if(null!=handleFlags&&handleFlags.size()>0){
            builder.and(event.handleFlag.in(handleFlags));
        }
        if(!StringUtils.isEmpty(createOrgId)){
            builder.and(event.createOrgId.eq(createOrgId));
        }

        query.where(builder).orderBy(event.createTime.desc()); //按照创建时间倒叙排

        return findAll(query);
    }

}
