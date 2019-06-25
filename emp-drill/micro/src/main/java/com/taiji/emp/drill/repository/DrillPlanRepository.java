package com.taiji.emp.drill.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.drill.entity.DrillPlan;
import com.taiji.emp.drill.entity.QDrillPlan;
import com.taiji.emp.drill.searchVo.DrillPlanSearchVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Repository
@Transactional(readOnly = true)
public class DrillPlanRepository extends BaseJpaRepository<DrillPlan,String> {

    /**
     * 新增或修改演练计划
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public DrillPlan save(DrillPlan entity) {
        Assert.notNull(entity,"entity对象不能为空");
        DrillPlan result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            DrillPlan temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据条件查询演练计划列表——分页
     * @param searchVo
     * @param pageable
     * @return
     */
    public Page<DrillPlan> findPage(DrillPlanSearchVo searchVo, Pageable pageable) {
        QDrillPlan drillPlan = QDrillPlan.drillPlan;
        JPQLQuery<DrillPlan> query = from(drillPlan);
        BooleanBuilder builder = new BooleanBuilder();

        String drillName = searchVo.getDrillName();
        String partOrgIds = searchVo.getPartOrgIds();
        String drillStartTime = searchVo.getDrillStartTime();
        String drillEndTime = searchVo.getDrillEndTime();
        String drillWayId = searchVo.getDrillWayId();
        String orgId = searchVo.getOrgId();

        if (StringUtils.hasText(drillName)){
            builder.and(drillPlan.drillName.contains(drillName));
        }

        if (StringUtils.hasText(partOrgIds)){
            builder.and(drillPlan.partOrgIds.eq(partOrgIds));
        }
        if (StringUtils.hasText(drillStartTime) && StringUtils.hasText(drillEndTime)){
            LocalDate startDrillTime = DateUtil.strToLocalDate(drillStartTime);
            LocalDate endDrillTime = DateUtil.strToLocalDate(drillEndTime);
            builder.and(drillPlan.drillTime.between(startDrillTime,endDrillTime));
        }

        if (StringUtils.hasText(drillStartTime) && !StringUtils.hasText(drillEndTime)){
            LocalDate startDrillTime = DateUtil.strToLocalDate(drillStartTime);
            builder.and(drillPlan.drillTime.goe(startDrillTime));
        }
        if (StringUtils.hasText(drillEndTime) && !StringUtils.hasText(drillStartTime)){
            LocalDate endDrillTime = DateUtil.strToLocalDate(drillEndTime);
            builder.and(drillPlan.drillTime.loe(endDrillTime));
        }
        if (StringUtils.hasText(drillWayId)){
            builder.and(drillPlan.drillWayId.eq(drillWayId));
        }

        if (StringUtils.hasText(orgId)){
            builder.and(drillPlan.orgId.contains(orgId));
        }

        builder.and(drillPlan.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(
                DrillPlan.class
                ,drillPlan.id
                ,drillPlan.drillName
                ,drillPlan.drillWayId
                ,drillPlan.drillWayName
                ,drillPlan.drillTime
                ,drillPlan.drillPlace
                ,drillPlan.partOrgIds
                ,drillPlan.partOrgNames
                ,drillPlan.content
                ,drillPlan.epPlanIds
                ,drillPlan.epPlanNames
                ,drillPlan.notes
                ,drillPlan.sendStatus
                ,drillPlan.reportStatus
                ,drillPlan.orgId
                ,drillPlan.orgName
        )).where(builder).orderBy(drillPlan.drillTime.desc());
        return findAll(query,pageable);
    }
}
