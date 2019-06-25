package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanTask;
import com.taiji.emp.res.entity.QPlanTask;
import com.taiji.emp.res.searchVo.planTask.PlanTaskListVo;
import com.taiji.emp.res.searchVo.planTask.PlanTaskPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class PlanTaskRepository extends BaseJpaRepository<PlanTask,String> {

    //带分页信息，查询预案任务设置管理列表
    public Page<PlanTask> findPage(PlanTaskPageVo planTaskPageVo, Pageable pageable){
        QPlanTask planTask = QPlanTask.planTask;
        JPQLQuery<PlanTask> query = from(planTask);

        BooleanBuilder builder = new BooleanBuilder();

        String principalName = planTaskPageVo.getPrincipalName();//负责部门名称
        String title = planTaskPageVo.getTitle();//任务名称
        String eventGradeId = planTaskPageVo.getEventGradeId();//适用响应级别
        String planId = planTaskPageVo.getPlanId();//预案ID
        String princiOrgId = planTaskPageVo.getPrinciOrgId(); //负责部门ID
        List<String> princiOrgIds = planTaskPageVo.getPrinciOrgIds();
        if(StringUtils.hasText(principalName)){
            builder.and(planTask.princiName.contains(principalName));
        }

        if(StringUtils.hasText(title)){
            builder.and(planTask.title.contains(title));
        }

        if(StringUtils.hasText(eventGradeId)){
            builder.and(planTask.eventGradeId.eq(eventGradeId));
        }

        if(StringUtils.hasText(planId)){
            builder.and(planTask.planID.eq(planId));
        }

        if(null!=princiOrgIds&&princiOrgIds.size()>0){
            builder.and(planTask.princiOrgId.in(princiOrgIds));
        }

        if(StringUtils.hasText(princiOrgId)){
            builder.and(planTask.princiOrgId.eq(princiOrgId));
        }
        builder.and(planTask.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(PlanTask.class
                        ,planTask.id
                        ,planTask.planID
                        ,planTask.eventGradeId
                        ,planTask.eventGradeName
                        ,planTask.title
                        ,planTask.content
                        ,planTask.princiName
                        ,planTask.princiTel
                        ,planTask.princiOrgId
                        ,planTask.princiOrgName
                )).where(builder)
                .orderBy(planTask.updateTime.desc());

        return findAll(query,pageable);
    }

    //不带分页信息，查询预案任务设置管理列表
    public List<PlanTask> findList(PlanTaskListVo planTaskListVo){
        QPlanTask planTask = QPlanTask.planTask;
        JPQLQuery<PlanTask> query = from(planTask);

        BooleanBuilder builder = new BooleanBuilder();

        String princiOrgName = planTaskListVo.getPrincipalName();//负责部门名称
        String title = planTaskListVo.getTitle();//任务名称
        String eventGradeId = planTaskListVo.getEventGradeId();//适用响应级别
        String planId = planTaskListVo.getPlanId();//预案ID
        List<String> princiOrgIds = planTaskListVo.getPrinciOrgIds(); //负责部门ID
        List<String> planIds = planTaskListVo.getPlanIds();
        if(StringUtils.hasText(princiOrgName)){
            builder.and(planTask.princiName.contains(princiOrgName));
        }

        if(StringUtils.hasText(title)){
            builder.and(planTask.title.contains(title));
        }

        if(StringUtils.hasText(eventGradeId)){
            builder.and(planTask.eventGradeId.eq(eventGradeId));
        }

        if(StringUtils.hasText(planId)){
            builder.and(planTask.planID.eq(planId));
        }

        if(null!=princiOrgIds&&princiOrgIds.size()>0){
            builder.and(planTask.princiOrgId.in(princiOrgIds));
        }

        if (!CollectionUtils.isEmpty(planIds)){
            builder.and(planTask.planID.in(planIds));
        }

        builder.and(planTask.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(PlanTask.class
                        ,planTask.id
                        ,planTask.planID
                        ,planTask.eventGradeId
                        ,planTask.eventGradeName
                        ,planTask.title
                        ,planTask.content
                        ,planTask.princiName
                        ,planTask.princiTel
                        ,planTask.princiOrgId
                        ,planTask.princiOrgName
                )).where(builder)
                .orderBy(planTask.updateTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public PlanTask save(PlanTask entity){
        Assert.notNull(entity,"PlanTask 对象不能为 null");

        PlanTask result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            PlanTask temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }
}
