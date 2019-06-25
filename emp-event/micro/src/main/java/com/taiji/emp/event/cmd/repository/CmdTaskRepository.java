package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.track.QTask;
import com.taiji.emp.event.cmd.entity.track.Task;
import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
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

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class CmdTaskRepository extends BaseJpaRepository<Task,String> {


    public List<Task> findList(TaskPageVo taskPageVo){
        JPQLQuery<Task> query = buildQuery(taskPageVo);
        return findAll(query);
    }

    public Page<Task> findPage(TaskPageVo taskPageVo, Pageable pageable){
        JPQLQuery<Task> query = buildQuery(taskPageVo);
        return findAll(query,pageable);
    }

    public boolean findOneByPlanTaskId(String planTaskId,String schemeId){
        QTask task = QTask.task;
        JPQLQuery<Task> query = from(task);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(task.planTaskId.eq(planTaskId));
        builder.and(task.schemeId.eq(schemeId));
        builder.and(task.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<Task> list = findAll(builder);
        if(list!=null && list.size()>0){
            return true;
        }else {
            return false;
        }
    }
    private JPQLQuery<Task> buildQuery(TaskPageVo taskPageVo){
        QTask task = QTask.task;
        JPQLQuery<Task> query = from(task);

        BooleanBuilder builder = new BooleanBuilder();
        String eventName = taskPageVo.getEventName();
        String schemeName = taskPageVo.getSchemeName();
        String taskName = taskPageVo.getTaskName();
        String schemeId = taskPageVo.getSchemeId();
        List<String> taskStatus = taskPageVo.getTaskStatus();
        List<LocalDateTime> createTimes = taskPageVo.getCreateTimes();
        LocalDateTime createStartTime = taskPageVo.getCreateStartTime();
        LocalDateTime createEndTime = taskPageVo.getCreateEndTime();
        if(StringUtils.hasText(eventName)){
            builder.and(task.eventName.contains(eventName));
        }
        if(StringUtils.hasText(schemeName)){
            builder.and(task.schemeId.eq(schemeId));
        }
        if(StringUtils.hasText(schemeName)){
            builder.and(task.schemeName.contains(schemeName));
        }
        if(StringUtils.hasText(taskName)){
            builder.and(task.name.contains(taskName));
        }

        if(!CollectionUtils.isEmpty(taskStatus)){
                builder.and(task.taskStatus.in(taskStatus));
        }

        if (null != createStartTime && null == createEndTime){
            builder.and(task.createTime.gt(createStartTime));
        }
        if (null == createStartTime && null != createEndTime){
            builder.and(task.createTime.lt(createEndTime));
        }
        if (null != createStartTime && null != createEndTime){
            builder.and(task.createTime.between(createStartTime,createEndTime));
        }
//        if(!CollectionUtils.isEmpty(createTimes)){
//            LocalDateTime time = DateUtil.strToLocalDateTime(params.getFirst("createTimes").toString());
//            builder.and(task.createTime.in(time));
//        }
        builder.and(task.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(
                Projections.bean(Task.class
                        ,task.id
                        ,task.schemeId
                        ,task.schemeName
                        //,task.eventId
                        ,task.eventName
                        //,task.planId
                        ,task.name
                        ,task.content
                        ,task.startTime
                        ,task.endTime
                        ,task.taskStatus
                        ,task.createTime
                        ,task.createOrgName
                )).where(builder)
                .orderBy(task.updateTime.desc());
        return query;
    }

    @Override
    @Transactional
    public Task save(Task entity){
        Assert.notNull(entity,"Task 对象不能为 null");

        Task result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            Task temp = findOne(entity.getId());
                BeanUtils.copyNonNullProperties(entity, temp);
                result = super.save(temp);
        }
        return result;
    }
}
