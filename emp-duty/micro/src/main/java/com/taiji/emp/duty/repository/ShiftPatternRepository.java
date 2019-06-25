package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.QShiftPattern;
import com.taiji.emp.duty.entity.ShiftPattern;
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
public class ShiftPatternRepository extends BaseJpaRepository<ShiftPattern,String> {

    /**
     * 新增或修改班次设置信息
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public ShiftPattern save(ShiftPattern entity){
        Assert.notNull(entity,"entity对象不能为空");
        ShiftPattern result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else{
            ShiftPattern temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }


    /**
     * 根据条件查询班次设置列表
     * @param patternId
     * @return
     */
    public List<ShiftPattern> findList(String patternId) {
        QShiftPattern shiftPattern = QShiftPattern.shiftPattern;

        JPQLQuery<ShiftPattern> query = from(shiftPattern);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(patternId)){
            builder.and(shiftPattern.patternId.eq(patternId));
        }
        builder.and(shiftPattern.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(ShiftPattern.class
                ,shiftPattern.id
                ,shiftPattern.patternId
                ,shiftPattern.shiftName
                ,shiftPattern.isToday
                ,shiftPattern.endTime
                ,shiftPattern.startTime
                ,shiftPattern.shiftSeq
        )).where(builder).orderBy(shiftPattern.shiftSeq.asc());
        return findAll(query);
    }


}
