package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.track.TaskFeedback;
import com.taiji.emp.event.cmd.repository.CmdTaskFeedBackRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class CmdTaskFeedBackService extends BaseService<TaskFeedback,String> {

    @Autowired
    private CmdTaskFeedBackRepository repository;

    public TaskFeedback create(TaskFeedback entity){
        Assert.notNull(entity,"Task 对象不能为 null");
        TaskFeedback result = repository.save(entity);
        return result;
    }

    public TaskFeedback findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        TaskFeedback result = repository.findOne(id);
        return result;
    }

    public List<TaskFeedback> findList(String taskId){
        List<TaskFeedback> result = repository.findList(taskId);
        return result;
    }



}
