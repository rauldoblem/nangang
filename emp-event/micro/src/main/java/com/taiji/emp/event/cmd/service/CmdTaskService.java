package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.track.Task;
import com.taiji.emp.event.cmd.searchVo.DispatchVo;
import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
import com.taiji.emp.event.common.constant.EventGlobal;
import org.springframework.data.domain.Pageable;
import com.taiji.emp.event.cmd.repository.CmdTaskRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class CmdTaskService extends BaseService<Task,String> {

    @Autowired
    private CmdTaskRepository repository;

    public Task create(Task entity){
        Assert.notNull(entity,"Task 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Task result = repository.save(entity);
        return result;
    }
    public Task update(Task entity,String id){
        Assert.notNull(entity,"Task 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        entity.setId(id);
        Task result = repository.save(entity);
        return result;
    }

    public Task findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Task result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Task entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }
    /**分页*/
    public Page<Task> findPage(TaskPageVo taskPageVo, Pageable pageable){
        Page<Task> result = repository.findPage(taskPageVo,pageable);
        return result;
    }
    /**不分页*/
    public List<Task> findList(TaskPageVo taskPageVo){
        List<Task> result = repository.findList(taskPageVo);
        return result;
    }
    /**下发*/
    public Task dispatch(DispatchVo vo){
        String id = vo.getTaskId();
        Task entity = repository.findOne(id);
        entity.setTaskStatus(EventGlobal.EVENT_TASK_SEND);
        Task result = repository.save(entity);
        return result;
    }
    /**根据planTaskId 查询当前数据是否存在 存在返回true*/
    public boolean findOneByPlanTaskId(String planTaskId, String schemeId){
        return repository.findOneByPlanTaskId(planTaskId,schemeId);
    }


}
