package com.taiji.emp.nangang.repository;

import com.mysema.commons.lang.Assert;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.DailyCheck;
import com.taiji.emp.nangang.entity.QDailyCheck;
import com.taiji.emp.nangang.searchVo.dailyCheck.DailyCheckPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class DailyCheckRepository extends BaseJpaRepository<DailyCheck,String> {

    /**
     * 保存或修改一条dailyCheck
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public DailyCheck save(DailyCheck entity) {

        Assert.notNull(entity,"dailyCheck对象不能为null");

        DailyCheck result;
        if (StringUtils.isEmpty(entity.getId())) {
            result = super.save(entity);
        }else {
            DailyCheck temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据条件（非id）查询单个dailyCheck
     * @param entity
     * @return
     */
    public List<DailyCheck> findOne(DailyCheck entity) {
        Assert.notNull(entity,"dailyCheck对象不能为null");
        QDailyCheck dailyCheck = QDailyCheck.dailyCheck;

        JPQLQuery<DailyCheck> query = from(dailyCheck);
        BooleanBuilder builder = new BooleanBuilder();
        LocalDate checkDate = entity.getCheckDate();
        String shiftPatternId = entity.getShiftPatternId();

        if (StringUtils.hasText(String.valueOf(checkDate))){
            builder.and(dailyCheck.checkDate.eq(checkDate));
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(shiftPatternId)){
            builder.and(dailyCheck.shiftPatternId.eq(shiftPatternId));
        }

        builder.and(dailyCheck.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(
                DailyCheck.class
                ,dailyCheck.id
                ,dailyCheck.checkDate
                ,dailyCheck.shiftPatternName
                ,dailyCheck.shiftPatternId
                ,dailyCheck.isShift
        )).where(builder);

        return findAll(query);
    }

    /**
     * 分页查询dailyCheck
     * @param dailyCheckPageVo
     * @param pageable
     * @return
     */
    public Page<DailyCheck> findPage(DailyCheckPageVo dailyCheckPageVo, Pageable pageable) {

        QDailyCheck dailyCheck = QDailyCheck.dailyCheck;
        JPQLQuery<DailyCheck> query = from(dailyCheck);

        BooleanBuilder builder = new BooleanBuilder();

        LocalDate checkDateStart = dailyCheckPageVo.getCheckDateStart();
        LocalDate checkDateEnd = dailyCheckPageVo.getCheckDateEnd();
        LocalDate todayDate = dailyCheckPageVo.getTodayDate();

        if (null != checkDateStart && null != checkDateEnd){
            builder.and(dailyCheck.checkDate.between(checkDateStart,checkDateEnd));
        }else if (null == checkDateStart && null != checkDateEnd){
            builder.and(dailyCheck.checkDate.loe(checkDateEnd));
        }else if (null != checkDateStart && null == checkDateEnd){
            builder.and(dailyCheck.checkDate.goe(checkDateStart));
        }

        builder.and(dailyCheck.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        builder.and(dailyCheck.shiftPatternId.notEqualsIgnoreCase("000001"));
        builder.and(dailyCheck.checkDate.notIn(todayDate));
        query.select(
                Projections.bean(DailyCheck.class
                        ,dailyCheck.id
                        ,dailyCheck.checkDate
                        ,dailyCheck.shiftPatternId
                        ,dailyCheck.shiftPatternName
                        ,dailyCheck.isShift
                        ,dailyCheck.createTime
                        ,dailyCheck.updateTime
                )).where(builder)
                .orderBy(dailyCheck.checkDate.desc());

        return findAll(query,pageable);
    }

}
