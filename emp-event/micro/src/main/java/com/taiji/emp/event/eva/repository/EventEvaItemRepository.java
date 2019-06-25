package com.taiji.emp.event.eva.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.eva.entity.EventEvaItem;
import com.taiji.emp.event.eva.entity.QEventEvaItem;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class EventEvaItemRepository extends BaseJpaRepository<EventEvaItem,String> {

    /**
     * 新增或修改
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public EventEvaItem save(EventEvaItem entity) {
        EventEvaItem result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            EventEvaItem temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据事件类型获取事件评估项列表
     * @param eventTypeId
     * @return
     */
    public List<EventEvaItem> findListByEventTypeId(String eventTypeId) {
        QEventEvaItem eventEvaItem = QEventEvaItem.eventEvaItem;
        JPQLQuery<EventEvaItem> query = from(eventEvaItem);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(eventTypeId)){
            builder.and(eventEvaItem.eventTypeId.eq(eventTypeId));
        }
        builder.and(eventEvaItem.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(
                EventEvaItem.class
                ,eventEvaItem.id
                ,eventEvaItem.orgId
                ,eventEvaItem.eventTypeId
                ,eventEvaItem.eventTypeName
                ,eventEvaItem.name
                ,eventEvaItem.interpret
                ,eventEvaItem.proportion
        )).where(builder).orderBy(eventEvaItem.updateTime.desc());
        return findAll(query);
    }
}
