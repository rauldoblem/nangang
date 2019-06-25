package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanMaterial;
import com.taiji.emp.res.entity.PlanSupport;
import com.taiji.emp.res.entity.QPlanSupport;
import com.taiji.emp.res.mapper.PlanMaterialMapper;
import com.taiji.emp.res.mapper.PlanSupportMapper;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportListVo;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportPageVo;
import com.taiji.emp.res.vo.PlanMaterialVo;
import com.taiji.emp.res.vo.PlanSupportVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PlanSupportRepository extends BaseJpaRepository<PlanSupport,String> {

    @Autowired
    PlanSupportMapper mapper;

    //带分页信息，查询预案社会依托资源管理基础列表
    public Page<PlanSupport> findPage(PlanSupportPageVo planSupportPageVo, Pageable pageable){
        QPlanSupport planSupport = QPlanSupport.planSupport;
        JPQLQuery<PlanSupport> query = from(planSupport);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planSupportPageVo.getPlanId();//适用预案类别
       // String eventGradeId = planSupportPageVo.getEventGradeId();//适用事件级别

        if(StringUtils.hasText(planId)){
            builder.and(planSupport.planId.contains(planId));
        }
//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planSupport.eventGradeId.contains(eventGradeId));
//        }

        query.select(
                Projections.bean(PlanSupport.class
                        ,planSupport.id
                        ,planSupport.planId
                        ,planSupport.eventGradeId
                        ,planSupport.eventGradeName
                        ,planSupport.supportId
                )).where(builder);
        return findAll(query,pageable);
    }

    //不带分页信息，查询预案社会依托资源管理基础列表
    public List<PlanSupport> findList(PlanSupportListVo planSupportListVo){
        QPlanSupport planSupport = QPlanSupport.planSupport;
        JPQLQuery<PlanSupport> query = from(planSupport);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planSupportListVo.getPlanId();//适用预案类别
       // String eventGradeId = planSupportListVo.getEventGradeId();//适用事件级别
        List<String> planIds = planSupportListVo.getPlanIds();//预案数组

        if(null!=planIds&&planIds.size()>0){
            builder.and(planSupport.planId.in(planIds));
        }

        if(StringUtils.hasText(planId)){
            builder.and(planSupport.planId.eq(planId));
        }
//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planSupport.eventGradeId.eq(eventGradeId));
//        }
        query.select(
                Projections.bean(PlanSupport.class
                        ,planSupport.id
                        ,planSupport.planId
                        ,planSupport.eventGradeId
                        ,planSupport.eventGradeName
                        ,planSupport.supportId
                )).where(builder);
        return findAll(query);
    }

    @Override
    @Transactional
    public PlanSupport save(PlanSupport entity){
        Assert.notNull(entity,"PlanSupport 对象不能为 null");

        PlanSupport result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            PlanSupport temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }

    @Transactional
    public PlanSupport deleteAfterSave(PlanSupportVo vo){
        Assert.notNull(vo,"PlanSupportVo不能为 null");
        PlanSupport result = new PlanSupport();
        //循环保存
        List<String> supportIds = vo.getSupportIds();
        for (String supportId:supportIds) {
            vo.setSupportId(supportId);
            PlanSupport entity = mapper.voToEntity(vo);
            result = super.save(entity);
        }
        return result;
    }

    //根据参数查询预案社会依托资源管理
    //参数planId、eventGradeID、expertId
    public List<PlanSupport> findPlanSupportByParms(PlanSupportVo planSupportVo){
        QPlanSupport planSupport = QPlanSupport.planSupport;
        JPQLQuery<PlanSupport> query = from(planSupport);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planSupportVo.getPlanId();//适用预案类别
       // String eventGradeId = planSupportVo.getEventGradeId();//适用事件级别
        String supportId = planSupportVo.getSupportId();//社会依托资源ID

        if(StringUtils.hasText(planId)){
            builder.and(planSupport.planId.contains(planId));
        }

//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planSupport.eventGradeId.contains(eventGradeId));
//        }

        if(StringUtils.hasText(supportId)){
            builder.and(planSupport.supportId.contains(supportId));
        }

        query.select(
                Projections.bean(PlanSupport.class
                        ,planSupport.id
                        ,planSupport.planId
                        ,planSupport.eventGradeId
                        ,planSupport.eventGradeName
                        ,planSupport.supportId
                )).where(builder);
        return findAll(query);
    }

    //根据预案ids查询对应的社会依托资源ids
    public List<String> findSupportsByPlanIds(List<String> planIds){
        QPlanSupport planSupport = QPlanSupport.planSupport;
        JPQLQuery<PlanSupport> query = from(planSupport);

        BooleanBuilder builder = new BooleanBuilder();

        if(null!=planIds&&planIds.size()>0){
            builder.and(planSupport.planId.in(planIds));
        }
        List<String> supports = query.select(planSupport.supportId)
                .where(builder)
                .groupBy(planSupport.supportId)
                .fetch();
        return supports;
    }
}
