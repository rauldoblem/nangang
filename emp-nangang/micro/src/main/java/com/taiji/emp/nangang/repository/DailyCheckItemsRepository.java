package com.taiji.emp.nangang.repository;


import com.mysema.commons.lang.Assert;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.DailyCheck;
import com.taiji.emp.nangang.entity.DailyCheckItems;
import com.taiji.emp.nangang.entity.QDailyCheckItems;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class DailyCheckItemsRepository extends BaseJpaRepository<DailyCheckItems,String> {

    @Override
    @Transactional
    public DailyCheckItems save(DailyCheckItems entity) {

        Assert.notNull(entity,"dailyCheckItems对象不能为null");

        DailyCheckItems result;
        if (StringUtils.isEmpty(entity.getId())) {
            result = super.save(entity);
        }else {
            DailyCheckItems temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    @Transactional
    public List<DailyCheckItems> save(List<DailyCheckItems> entitys) {

        boolean empty = CollectionUtils.isEmpty(entitys);
        org.springframework.util.Assert.isTrue(!empty , "List<DailyCheckItems>对象不能为null");
        List<DailyCheckItems> result = super.save(entitys);
        return result;
    }

    /**
     * 根据dailycheckId查询items的list
     * @param dailyCheckId
     * @return
     */
    public List<DailyCheckItems> findAll(String dailyCheckId) {
        Assert.notNull(dailyCheckId,"dailyCheckId不能为null");
        QDailyCheckItems dailyCheckItems = QDailyCheckItems.dailyCheckItems;

        JPQLQuery<DailyCheckItems> query = from(dailyCheckItems);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNotBlank(dailyCheckId)){
            builder.and(dailyCheckItems.dailycheckId.eq(dailyCheckId));
        }
        query.select(Projections.bean(
                DailyCheckItems.class
                ,dailyCheckItems.id
                ,dailyCheckItems.checkItemId
                ,dailyCheckItems.checkItemContent
                ,dailyCheckItems.checkResult
                ,dailyCheckItems.dailycheckId
                ,dailyCheckItems.updateTime
        )).where(builder);
        return findAll(query);
    }
}
