package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanTeam;
import com.taiji.emp.res.entity.QPlanTeam;
import com.taiji.emp.res.mapper.PlanTeamMapper;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamListVo;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamPageVo;
import com.taiji.emp.res.vo.PlanTeamVo;
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
public class PlanTeamRepository extends BaseJpaRepository<PlanTeam,String> {

    @Autowired
    PlanTeamMapper mapper;

    //带分页信息，查询预案团队管理基础列表
    public Page<PlanTeam> findPage(PlanTeamPageVo planTeamPageVo, Pageable pageable){
        QPlanTeam planTeam = QPlanTeam.planTeam;
        JPQLQuery<PlanTeam> query = from(planTeam);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planTeamPageVo.getPlanId();//适用预案类别：传入数组(多选)
        //String eventGradeId = planTeamPageVo.getEventGradeId();//适用事件级别：传入数组(多选)

        if(StringUtils.hasText(planId)){
            builder.and(planTeam.planId.contains(planId));
        }
//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planTeam.eventGradeId.contains(eventGradeId));
//        }

        query.select(
                Projections.bean(PlanTeam.class
                        ,planTeam.id
                        ,planTeam.planId
                        ,planTeam.eventGradeId
                        ,planTeam.eventGradeName
                        ,planTeam.teamId
                )).where(builder);
        return findAll(query,pageable);
    }

    //不带分页信息，查询预案团队管理基础列表
    public List<PlanTeam> findList(PlanTeamListVo planTeamListVo){
        QPlanTeam planTeam = QPlanTeam.planTeam;
        JPQLQuery<PlanTeam> query = from(planTeam);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planTeamListVo.getPlanId();//适用预案
       // String eventGradeId = planTeamListVo.getEventGradeId();//适用事件级别
        List<String> planIds = planTeamListVo.getPlanIds();//预案数组

        if(null!=planIds&&planIds.size()>0){
            builder.and(planTeam.planId.in(planIds));
        }

        if(StringUtils.hasText(planId)){
            builder.and(planTeam.planId.contains(planId));
        }
//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planTeam.eventGradeId.contains(eventGradeId));
//        }

        query.select(
                Projections.bean(PlanTeam.class
                        ,planTeam.id
                        ,planTeam.planId
                        ,planTeam.eventGradeId
                        ,planTeam.eventGradeName
                        ,planTeam.teamId
                )).where(builder);
        return findAll(query);
    }

    @Override
    @Transactional
    public PlanTeam save(PlanTeam entity){
        Assert.notNull(entity,"PlanTeam 对象不能为 null");

        PlanTeam result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            PlanTeam temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }

    @Transactional
    public PlanTeam deleteAfterSave(PlanTeamVo vo){
        Assert.notNull(vo,"PlanTeamVo不能为 null");
        PlanTeam result = new PlanTeam();
        //循环保存
        List<String> teamIds = vo.getTeamIds();
        for (String teamId:teamIds) {
            vo.setTeamId(teamId);
            vo.setPlanId(vo.getPlanId());
            PlanTeam entity = mapper.voToEntity(vo);
            result = super.save(entity);
        }
        return result;
    }


    //根据参数查询预案团队管理
    //参数planId、eventGradeID、expertId
    public List<PlanTeam> findPlanTeamByParms(PlanTeamVo planTeamVo){
        QPlanTeam planTeam = QPlanTeam.planTeam;
        JPQLQuery<PlanTeam> query = from(planTeam);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planTeamVo.getPlanId();//适用预案类别
       // String eventGradeId = planTeamVo.getEventGradeId();//适用事件级别
        String teamId = planTeamVo.getTeamId();//专家ID

        if(StringUtils.hasText(planId)){
            builder.and(planTeam.planId.eq(planId));
        }

//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planTeam.eventGradeId.eq(eventGradeId));
//        }

        if(StringUtils.hasText(teamId)){
            builder.and(planTeam.teamId.eq(teamId));
        }

        query.select(
                Projections.bean(PlanTeam.class
                        ,planTeam.id
                        ,planTeam.planId
                        ,planTeam.eventGradeId
                        ,planTeam.eventGradeName
                        ,planTeam.teamId
                )).where(builder);
        return findAll(query);
    }

    //根据预案ids查询对应的团队ids
    public List<String> findTeamsByPlanIds(List<String> planIds){
        QPlanTeam planTeam = QPlanTeam.planTeam;
        JPQLQuery<PlanTeam> query = from(planTeam);

        BooleanBuilder builder = new BooleanBuilder();

        if(null!=planIds&&planIds.size()>0){
            builder.and(planTeam.planId.in(planIds));
        }
        List<String> teams = query.select(planTeam.teamId)
                .where(builder)
                .groupBy(planTeam.teamId)
                .fetch();
        return teams;
    }
}
