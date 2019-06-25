package com.taiji.emp.drill.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.drill.entity.DrillScheme;
import com.taiji.emp.drill.entity.QDrillScheme;
import com.taiji.emp.drill.searchVo.DrillSchemeSearchVo;
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
import java.time.LocalDateTime;

@Repository
@Transactional(readOnly = true)
public class DrillSchemeRepository extends BaseJpaRepository<DrillScheme,String> {

    /**
     * 新增或修改 演练方案
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public DrillScheme save(DrillScheme entity){
        Assert.notNull(entity,"entity对象不能为空");
        DrillScheme result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            DrillScheme temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据条件查询演练方案列表——分页
     * @param searchVo
     * @param page
     * @return
     */
    public Page<DrillScheme> findPage(DrillSchemeSearchVo searchVo, Pageable page) {
        QDrillScheme drillScheme = QDrillScheme.drillScheme;
        JPQLQuery<DrillScheme> query = from(drillScheme);
        BooleanBuilder builder = new BooleanBuilder();

        String drillName = searchVo.getDrillName();
        String summary = searchVo.getSummary();
        String orgId = searchVo.getOrgId();
        String createStartTime = searchVo.getDrillStartTime();
        String createEndTime = searchVo.getDrillEndTime();

        if (StringUtils.hasText(drillName)){
            builder.and(drillScheme.drillName.contains(drillName));
        }

        if (StringUtils.hasText(summary)){
            builder.and(drillScheme.summary.contains(summary));
        }

        if (StringUtils.hasText(orgId)){
            builder.and(drillScheme.orgId.eq(orgId));
        }

        if (StringUtils.hasText(createStartTime) && StringUtils.hasText(createEndTime)){
            builder.and(drillScheme.createTime
                    .between(DateUtil.strToLocalDateTime(createStartTime),DateUtil.strToLocalDateTime(createEndTime)));
        }

        if (StringUtils.hasText(createStartTime) && !StringUtils.hasText(createEndTime)) {
            LocalDateTime startDrillTime = DateUtil.strToLocalDateTime(createStartTime);
            builder.and(drillScheme.createTime.goe(startDrillTime));
        }
        if (StringUtils.hasText(createEndTime) && !StringUtils.hasText(createStartTime)){
            LocalDateTime startDrillTime1 = DateUtil.strToLocalDateTime(createEndTime);
            builder.and(drillScheme.createTime.loe(startDrillTime1));
        }


        builder.and(drillScheme.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(DrillScheme.class
                ,drillScheme.id
                ,drillScheme.drillPlanId
                ,drillScheme.drillPlanName
                ,drillScheme.drillName
                ,drillScheme.summary
                ,drillScheme.epPlanIds
                ,drillScheme.epPlanNames
                ,drillScheme.sendStatus
                ,drillScheme.reportStatus
                ,drillScheme.orgId
                ,drillScheme.orgName
                ,drillScheme.createBy
                ,drillScheme.createTime
        )).where(builder).orderBy(drillScheme.updateTime.desc());
        return findAll(query,page);
    }
}
