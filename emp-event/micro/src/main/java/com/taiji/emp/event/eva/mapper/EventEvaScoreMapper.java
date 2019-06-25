package com.taiji.emp.event.eva.mapper;

import com.taiji.emp.event.eva.entity.EventEvaItem;
import com.taiji.emp.event.eva.entity.EventEvaScore;
import com.taiji.emp.event.eva.vo.EventEvaItemVo;
import com.taiji.emp.event.eva.vo.EventEvaScoreVo;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件评估分值 EventEvaScoreMapper
 * @author qzp-pc
 * @date 2018年11月08日14:28:18
 */
@Mapper(componentModel = "spring")
public interface EventEvaScoreMapper extends BaseMapper<EventEvaScore,EventEvaScoreVo> {

    @Override
    default List<EventEvaScore> voListToEntityList(List<EventEvaScoreVo> voList) {
        if ( voList == null) {
            return null;
        }
        List<EventEvaScore> list = new ArrayList<>(voList.size());
        for (EventEvaScoreVo entityVo : voList){
            EventEvaScore entity = new EventEvaScore();
            if (!StringUtils.isEmpty(entityVo.getId())){
                entity.setId(entityVo.getId());
            }
            entity.setReportId(entityVo.getReportId());
            entity.setScore(entityVo.getScore());
            EventEvaItemVo eventEvaItemVo = entityVo.getEventEvaItem();
            EventEvaItem eventEvaItem = getEventEvaItemVo(eventEvaItemVo);
            String itemId = entityVo.getEventEvaItem().getId();
            if (!StringUtils.isEmpty(itemId)){
                eventEvaItem.setId(itemId);
            }
            eventEvaItem.setCreateBy(entityVo.getEventEvaItem().getCreateBy());
            eventEvaItem.setUpdateBy(entityVo.getEventEvaItem().getUpdateBy());
            entity.setEventEvaItem(eventEvaItem);
            list.add(entity);
        }
        return list;
    }

    default EventEvaItem getEventEvaItemVo(EventEvaItemVo vo){
        if ( vo == null) {
            return null;
        }
        EventEvaItem entity = new EventEvaItem();
        entity.setOrgId(vo.getOrgId());
        entity.setEventTypeId(vo.getEventTypeId());
        entity.setEventTypeName(vo.getEventTypeName());
        entity.setName(vo.getName());
        entity.setInterpret(vo.getInterpret());
        entity.setProportion(vo.getProportion());
        return entity;
    }
}
