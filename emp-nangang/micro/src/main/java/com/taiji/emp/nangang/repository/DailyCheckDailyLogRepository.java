package com.taiji.emp.nangang.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.DailyCheckDailyLog;
import com.taiji.emp.nangang.entity.QDailyCheckDailyLog;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yhcookie
 * @date 2018/12/12 10:53
 */
@Repository
@Transactional(readOnly = true)
public class DailyCheckDailyLogRepository extends BaseJpaRepository<DailyCheckDailyLog,String> {

    /**
     * 根据dailyCheckItemsVoId或去对应的dailyLogId
     * @param dailyCheckItemsVoId
     * @return
     */
    public String findDailyLogId(String dailyCheckItemsVoId) {
        QDailyCheckDailyLog dailyCheckDailyLog = QDailyCheckDailyLog.dailyCheckDailyLog;
        JPQLQuery<DailyCheckDailyLog> query = from(dailyCheckDailyLog);

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.isNotBlank(dailyCheckItemsVoId)){
            builder.and(dailyCheckDailyLog.checkItemId.eq(dailyCheckItemsVoId));
        }

        DailyCheckDailyLog queryResult = query.select(Projections.bean(
                DailyCheckDailyLog.class
                , dailyCheckDailyLog.dailylogId
        )).where(builder).fetchOne();
        //结果为空 返回结果为null
        String result = null;
        if(null != queryResult){
            result = queryResult.getDailylogId();
        }
        return result;
    }
}
