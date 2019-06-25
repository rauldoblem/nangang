package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanExpert;
import com.taiji.emp.res.entity.QPlanExpert;
import com.taiji.emp.res.mapper.PlanExpertMapper;
import com.taiji.emp.res.mapper.PlanMaterialMapper;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertPageVo;
import com.taiji.emp.res.vo.PlanExpertVo;
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
public class PlanExpertRepository extends BaseJpaRepository<PlanExpert,String> {

    @Autowired
    PlanExpertMapper mapper;
    //带分页信息，查询预案专家管理基础列表
    public Page<PlanExpert> findPage(PlanExpertPageVo planExpertPageVo, Pageable pageable){
        QPlanExpert planExpert = QPlanExpert.planExpert;
        JPQLQuery<PlanExpert> query = from(planExpert);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planExpertPageVo.getPlanId();//适用预案类别
        //String eventGradeId = planExpertPageVo.getEventGradeId();//适用事件级别

        if(StringUtils.hasText(planId)){
            builder.and(planExpert.planId.contains(planId));
        }
//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planExpert.eventGradeId.contains(eventGradeId));
//        }

        query.select(
                Projections.bean(PlanExpert.class
                        ,planExpert.id
                        ,planExpert.planId
                        ,planExpert.eventGradeId
                        ,planExpert.eventGradeName
                        ,planExpert.expertId
                )).where(builder);
        return findAll(query,pageable);
    }

    //不带分页信息，查询预案专家管理基础列表
    public List<PlanExpert> findList(PlanExpertListVo planExpertListVo){
        QPlanExpert planExpert = QPlanExpert.planExpert;
        JPQLQuery<PlanExpert> query = from(planExpert);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planExpertListVo.getPlanId();//适用预案
       // String eventGradeId = planExpertListVo.getEventGradeId();//适用事件级别
        List<String> planIds = planExpertListVo.getPlanIds();//预案数组

        if(null!=planIds&&planIds.size()>0){
            builder.and(planExpert.planId.in(planIds));
        }

        if(StringUtils.hasText(planId)){
            builder.and(planExpert.planId.contains(planId));
        }
//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planExpert.eventGradeId.contains(eventGradeId));
//        }

        query.select(
                Projections.bean(PlanExpert.class
                        ,planExpert.id
                        ,planExpert.planId
                        ,planExpert.eventGradeId
                        ,planExpert.eventGradeName
                        ,planExpert.expertId
                )).where(builder);
        return findAll(query);
    }

    @Override
    @Transactional
    public PlanExpert save(PlanExpert entity){
        Assert.notNull(entity,"PlanExpert 对象不能为 null");

        PlanExpert result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            PlanExpert temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }

    @Transactional
    public PlanExpert deleteAfterSave(PlanExpertVo vo){
        Assert.notNull(vo,"PlanMaterialVo不能为 null");
        PlanExpert result = new PlanExpert();
        //循环保存
        List<String> expertIds = vo.getExpertIds();
        for (String expertId:expertIds) {
            vo.setExpertId(expertId);
            PlanExpert entity = mapper.voToEntity(vo);
            result = super.save(entity);
        }
        return result;
    }

    //根据参数查询预案专家管理
    //参数planId、eventGradeID、expertId
    public List<PlanExpert> findPlanExpertByParms(PlanExpertVo planExpertVo){
        QPlanExpert planExpert = QPlanExpert.planExpert;
        JPQLQuery<PlanExpert> query = from(planExpert);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planExpertVo.getPlanId();//适用预案类别
       // String eventGradeId = planExpertVo.getEventGradeID();//适用事件级别
        String expertId = planExpertVo.getExpertId();//专家ID

        if(StringUtils.hasText(planId)){
            builder.and(planExpert.planId.contains(planId));
        }

//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planExpert.eventGradeId.contains(eventGradeId));
//        }

        if(StringUtils.hasText(expertId)){
            builder.and(planExpert.expertId.contains(expertId));
        }

        query.select(
                Projections.bean(PlanExpert.class
                        ,planExpert.id
                        ,planExpert.planId
                        ,planExpert.eventGradeId
                        ,planExpert.eventGradeName
                        ,planExpert.expertId
                )).where(builder);
        return findAll(query);
    }

    //根据预案ids查询对应的专家ids
    public List<String> findExpertsByPlanIds(List<String> planIds){
        QPlanExpert planExpert = QPlanExpert.planExpert;
        JPQLQuery<PlanExpert> query = from(planExpert);

        BooleanBuilder builder = new BooleanBuilder();

        if(null!=planIds&&planIds.size()>0){
            builder.and(planExpert.planId.in(planIds));
        }
        List<String> experts = query.select(planExpert.expertId)
                .where(builder)
                .groupBy(planExpert.expertId)
                .fetch();
        return experts;
    }

}
