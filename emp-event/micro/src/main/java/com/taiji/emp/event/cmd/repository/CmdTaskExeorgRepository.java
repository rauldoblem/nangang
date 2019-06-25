package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.track.QTask;
import com.taiji.emp.event.cmd.entity.track.QTaskExeorg;
import com.taiji.emp.event.cmd.entity.track.Task;
import com.taiji.emp.event.cmd.entity.track.TaskExeorg;
import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
import com.taiji.emp.event.cmd.searchVo.TimeAxisTaskVo;
import com.taiji.emp.event.common.constant.EventGlobal;
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
public class CmdTaskExeorgRepository extends BaseJpaRepository<TaskExeorg,String> {

    //根据taskId获取对应的TaskExeorg
    public TaskExeorg findOneByTaskId(String taskId,String orgId){
        QTaskExeorg taskExeorg = QTaskExeorg.taskExeorg;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(taskExeorg.task.id.eq(taskId));
        if (!StringUtils.isEmpty(orgId)){
            builder.and(taskExeorg.orgId.eq(orgId));
        }
        return findOne(builder);
    }

    /**
     * 根据schemeId 获取多条信息 并根据下发时间排序
     * @return
     */
    public List<TaskExeorg> findListBySchemeId(TimeAxisTaskVo vo){
        QTaskExeorg taskExeorg = QTaskExeorg.taskExeorg;
        JPQLQuery<TaskExeorg> query = from(taskExeorg);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(taskExeorg.task.schemeId.eq(vo.getSchemeId()));
        if(vo.getTaskStatus()!=null) {
            builder.and(taskExeorg.task.taskStatus.in(vo.getTaskStatus()));
        }else {
            builder.and(taskExeorg.task.taskStatus.in(EventGlobal.EVENT_TASK_DONE,EventGlobal.EVENT_TASK_SEND));
        }
        query.where(builder).orderBy(taskExeorg.sendTime.desc());
        return findAll(query);
    }


    @Override
    @Transactional
    public TaskExeorg save(TaskExeorg entity){
        Assert.notNull(entity,"TaskExeorg 对象不能为 null");
        TaskExeorg result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            TaskExeorg temp = findOne(entity.getId());
                BeanUtils.copyNonNullProperties(entity, temp);
                result = super.save(temp);
        }
        return result;
    }


    public Page<TaskExeorg> findPage(TaskPageVo taskPageVo, Pageable pageable){
        JPQLQuery<TaskExeorg> query = buildQuery(taskPageVo);
        return findAll(query,pageable);
    }
    public List<TaskExeorg> findList(TaskPageVo taskPageVo){
        JPQLQuery<TaskExeorg> query = buildQuery(taskPageVo);
        return findAll(query);
    }
    private JPQLQuery<TaskExeorg> buildQuery(TaskPageVo taskPageVo){
        QTaskExeorg taskExeorg = QTaskExeorg.taskExeorg;
        JPQLQuery<TaskExeorg> query = from(taskExeorg);

        BooleanBuilder builder = new BooleanBuilder();
        String eventName = taskPageVo.getEventName();
        String schemeName = taskPageVo.getSchemeName();
        String taskName = taskPageVo.getTaskName();
        String schemeId = taskPageVo.getSchemeId();
        List<String> taskStatus = taskPageVo.getTaskStatus();
        LocalDateTime createStartTime = taskPageVo.getCreateStartTime();
        LocalDateTime createEndTime = taskPageVo.getCreateEndTime();
        String orgId = taskPageVo.getOrgId();
        String flag = taskPageVo.getFlag();
        if(StringUtils.hasText(eventName)){
            builder.and(taskExeorg.task.eventName.contains(eventName));
        }
        if(StringUtils.hasText(flag)){
            if(StringUtils.hasText(orgId) && orgId != null){
                if(EventGlobal.EVENT_TASK_UNSEND.equals(flag)){
                    builder.and(taskExeorg.task.createOrgId.eq(orgId));
                }else {
                    if(StringUtils.hasText(orgId)) {
                        builder.and(taskExeorg.orgId.eq(orgId));
                    }
                }
            }
        }
        if(StringUtils.hasText(schemeId)){
            builder.and(taskExeorg.task.schemeId.eq(schemeId));
        }
        if(StringUtils.hasText(schemeName)){
            builder.and(taskExeorg.task.schemeName.contains(schemeName));
        }
        if(StringUtils.hasText(taskName)){
            builder.and(taskExeorg.task.name.contains(taskName));
        }

        if(!CollectionUtils.isEmpty(taskStatus)){
            builder.and(taskExeorg.task.taskStatus.in(taskStatus));
        }

        if (null != createStartTime && null == createEndTime){
            builder.and(taskExeorg.task.createTime.gt(createStartTime));
        }
        if (null == createStartTime && null != createEndTime){
            builder.and(taskExeorg.task.createTime.lt(createEndTime));
        }
        if (null != createStartTime && null != createEndTime){
            builder.and(taskExeorg.task.createTime.between(createStartTime,createEndTime));
        }
       /* if (StringUtils.hasText(orgId) && orgId != null){
            builder.and(taskExeorg.orgId.eq(orgId));
        }*/
        builder.and(taskExeorg.task.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(
                Projections.bean(TaskExeorg.class
                        ,taskExeorg.id
                        ,taskExeorg.orgId
                        ,taskExeorg.sendTime
                        ,taskExeorg.orgName
                        ,taskExeorg.principal
                        ,taskExeorg.principalTel
                        ,taskExeorg.task
                )).where(builder)
                .orderBy(taskExeorg.task.createTime.desc());
        return query;
    }











}
