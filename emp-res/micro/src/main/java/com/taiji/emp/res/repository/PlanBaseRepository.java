package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanBase;
import com.taiji.emp.res.entity.QPlanBase;
import com.taiji.emp.res.searchVo.planBase.PlanBaseListVo;
import com.taiji.emp.res.searchVo.planBase.PlanBasePageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class PlanBaseRepository extends BaseJpaRepository<PlanBase,String> {

    //带分页信息，查询预案管理基础列表
    public Page<PlanBase> findPage(PlanBasePageVo planBasePageVo, Pageable pageable){
        QPlanBase planBase = QPlanBase.planBase;
        JPQLQuery<PlanBase> query = from(planBase);

        BooleanBuilder builder = new BooleanBuilder();

        String name = planBasePageVo.getName();//预案名称
        List<String> eventTypeIds = planBasePageVo.getEventTypeIds();//适用事件类别：传入数组(多选)
        String planTypeId = planBasePageVo.getPlanTypeId();//预案类型
        String createOrgId = planBasePageVo.getCreateOrgId(); //创建单位
        String planCalTreeId = planBasePageVo.getPlanCaltreeId();//预案目录id
        List<String> createOrgIds = planBasePageVo.getCreateOrgIds();
        List<String> selectedPlanIds = planBasePageVo.getSelectedPlanIds();

        if(StringUtils.hasText(name)){
            builder.and(planBase.name.contains(name));
        }
        if(null!=eventTypeIds&&eventTypeIds.size()>0){
            builder.and(planBase.eventTypeId.in(eventTypeIds));
        }

        if(StringUtils.hasText(planTypeId)){
            builder.and(planBase.planTypeId.eq(planTypeId));
        }
        if(null!=selectedPlanIds&&selectedPlanIds.size()>0){
            builder.and(planBase.planTypeId.in(selectedPlanIds));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(planBase.createOrgId.eq(createOrgId));
        }

        if(null!=createOrgIds&&createOrgIds.size()>0){
            builder.and(planBase.createOrgId.in(createOrgIds));
        }

        if(StringUtils.hasText(planCalTreeId)){
            if (!planCalTreeId.equals("1")){
                builder.and(planBase.planCalTreeId.eq(planCalTreeId));
            }
        }

        builder.and(planBase.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(PlanBase.class
                        ,planBase.id
                        ,planBase.name
                        ,planBase.unit
                        ,planBase.planTypeId
                        ,planBase.planTypeName
                        ,planBase.eventTypeId
                        ,planBase.eventTypeName
                        ,planBase.planStatusId
                        ,planBase.planStatusName
                        ,planBase.compileTime
                        ,planBase.planDescri
                        ,planBase.notes
                        ,planBase.createOrgId
                        ,planBase.planCalTreeId
                )).where(builder)
                .orderBy(planBase.updateTime.desc());

        return findAll(query,pageable);
    }

    //不带分页信息，查询预案管理基础列表
    public List<PlanBase> findList(PlanBaseListVo planBaseListVo){
        QPlanBase planBase = QPlanBase.planBase;
        JPQLQuery<PlanBase> query = from(planBase);

        BooleanBuilder builder = new BooleanBuilder();

        String name = planBaseListVo.getName();//预案名称
        List<String> eventTypeIds = planBaseListVo.getEventTypeIds();//适用事件类别：传入数组(多选)
        String planTypeId = planBaseListVo.getPlanTypeId();//预案类型
        String createOrgId = planBaseListVo.getCreateOrgId(); //创建单位
        String planCalTreeId = planBaseListVo.getPlanCaltreeId();//预案目录id
        List<String> createOrgIds = planBaseListVo.getCreateOrgIds();
        List<String> selectedPlanIds = planBaseListVo.getSelectedPlanIds();

        if(StringUtils.hasText(name)){
            builder.and(planBase.name.contains(name));
        }
        if(null!=eventTypeIds&&eventTypeIds.size()>0){
            builder.and(planBase.eventTypeId.in(eventTypeIds));
        }

        if(StringUtils.hasText(planTypeId)){
            builder.and(planBase.planTypeId.eq(planTypeId));
        }
        if(null!=selectedPlanIds&&selectedPlanIds.size()>0){
            builder.and(planBase.planTypeId.in(selectedPlanIds));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(planBase.createOrgId.eq(createOrgId));
        }
        if(null!=createOrgIds&&createOrgIds.size()>0){
            builder.and(planBase.createOrgId.in(createOrgIds));
        }

        if(StringUtils.hasText(planCalTreeId)){
            builder.and(planBase.planCalTreeId.eq(planCalTreeId));
        }

        builder.and(planBase.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(PlanBase.class
                        ,planBase.id
                        ,planBase.name
                        ,planBase.unit
                        ,planBase.planTypeId
                        ,planBase.planTypeName
                        ,planBase.eventTypeId
                        ,planBase.eventTypeName
                        ,planBase.planStatusId
                        ,planBase.planStatusName
                        ,planBase.compileTime
                        ,planBase.planDescri
                        ,planBase.notes
                        ,planBase.createOrgId
                        ,planBase.planCalTreeId
                )).where(builder)
                .orderBy(planBase.updateTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public PlanBase save(PlanBase entity){
        Assert.notNull(entity,"PlanBase 对象不能为 null");

        PlanBase result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            PlanBase temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }
}
