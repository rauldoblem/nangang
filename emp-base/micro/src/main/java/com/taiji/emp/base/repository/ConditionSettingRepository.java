package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.ConditionSetting;
import com.taiji.emp.base.entity.QConditionSetting;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/29 15:37
 */
@Repository
@Transactional(readOnly = true)
public class ConditionSettingRepository extends BaseJpaRepository<ConditionSetting,String>{
    /**
     * 根据事件类型Id，获取该事件类型不同等级的应急响应条件列表
     * @param eventTypeId
     * @return
     */
    public List<ConditionSetting> findListByEventId(String eventTypeId) {

        QConditionSetting conditionSetting1 = QConditionSetting.conditionSetting1;
        JPQLQuery<ConditionSetting> query = from(conditionSetting1);
        BooleanBuilder builder = new BooleanBuilder();
        if(!StringUtils.isEmpty(eventTypeId)){
            builder.and(conditionSetting1.eventTypeId.eq(eventTypeId));
        }
        return findAll(query.where(builder));
    }
}
