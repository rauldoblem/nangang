package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.track.QTaskFeedback;
import com.taiji.emp.event.cmd.entity.track.TaskFeedback;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class CmdTaskFeedBackRepository extends BaseJpaRepository<TaskFeedback,String> {

    /**
     * 根据taskId 获取反馈信息List
     * @param taskId
     * @return
     */
    public List<TaskFeedback> findList(String taskId){
        QTaskFeedback taskFeedback = QTaskFeedback.taskFeedback;
        JPQLQuery<TaskFeedback> query = from(taskFeedback);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(taskFeedback.taskId.eq(taskId));
        query.select(
                Projections.bean(TaskFeedback.class
                        ,taskFeedback.id
                        ,taskFeedback.taskOrgId
                        ,taskFeedback.content
                        ,taskFeedback.feedbackBy
                        ,taskFeedback.feedbackTime
                        ,taskFeedback.completeStatus
                        ,taskFeedback.taskId
                        ,taskFeedback.feedbackType
                )).where(builder)
                .orderBy(taskFeedback.feedbackTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public TaskFeedback save(TaskFeedback entity){
        Assert.notNull(entity,"Task 对象不能为 null");

        TaskFeedback result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            TaskFeedback temp = findOne(entity.getId());
                BeanUtils.copyNonNullProperties(entity, temp);
                result = super.save(temp);
        }
        return result;
    }
}
