package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.dailylog.DailyLogTreatment;
import com.taiji.emp.duty.entity.dailylog.QDailyLogTreatment;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DailyLogTreatmentRepository extends BaseJpaRepository<DailyLogTreatment,String> {

    /**
     * 添加值班日志办理信息
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public DailyLogTreatment save(DailyLogTreatment entity){
        Assert.notNull(entity,"entity对象不能为空");
        DailyLogTreatment result = super.save(entity);
        return result;
    }

    /**
     * 根据值班日志ID获取办理状态列表
     * @param dlogId
     * @return
     */
    public List<DailyLogTreatment> findByDlogId(String dlogId) {
        QDailyLogTreatment dailyLogTreatment = QDailyLogTreatment.dailyLogTreatment;
        JPQLQuery<DailyLogTreatment> query = from(dailyLogTreatment);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(dlogId)){
            builder.and(dailyLogTreatment.dailyLog.id.eq(dlogId));
        }
        query.select(Projections.bean(
                DailyLogTreatment.class
                ,dailyLogTreatment.id
                ,dailyLogTreatment.dailyLog
                ,dailyLogTreatment.dlogTreatment
                ,dailyLogTreatment.treatBy
                ,dailyLogTreatment.treatTime
                ,dailyLogTreatment.treatStatus
        )).where(builder);
        return findAll(query);
    }
}
