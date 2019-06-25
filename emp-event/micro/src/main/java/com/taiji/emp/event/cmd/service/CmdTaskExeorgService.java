package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.track.TaskExeorg;
import com.taiji.emp.event.cmd.repository.CmdTaskExeorgRepository;
import com.taiji.emp.event.cmd.searchVo.TaskPageVo;
import com.taiji.emp.event.cmd.searchVo.TimeAxisTaskVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class CmdTaskExeorgService extends BaseService<TaskExeorg,String> {

    private CmdTaskExeorgRepository repository;

    public TaskExeorg create(TaskExeorg entity){
        Assert.notNull(entity,"Task 对象不能为 null");
        TaskExeorg result = repository.save(entity);
        return result;
    }

    public TaskExeorg update(TaskExeorg entity,String taskId){
        Assert.notNull(entity,"Task 对象不能为 null");
        String id = findOneByTaskId(taskId,null).getId();
        Assert.notNull(id,"id 不能为 null");
        entity.setId(id);
        TaskExeorg result = repository.save(entity);
        return result;
    }

    /**
     * 根据taskId获取单条 TaskExeorg
     * @param taskId
     * @return
     */
    public TaskExeorg findOneByTaskId(String taskId,String orgId){
        return repository.findOneByTaskId(taskId,orgId);
    }

    /**
     * 根据taskId,删除对于的 TaskExeorg
     * @param taskId
     */
    public void deleteByTaskId(String taskId){
        repository.delete(findOneByTaskId(taskId,null).getId());
    }

    /**
     * 根据schemeId获取多条,也可传入状态
     * @return
     */
    public List<TaskExeorg> findListBySchemeId(TimeAxisTaskVo vo){
        return repository.findListBySchemeId(vo);
    }


    public TaskExeorg findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");
        TaskExeorg entity = repository.findOne(id);
        return entity;
    }



    /**分页*/
    public Page<TaskExeorg> findPage(TaskPageVo taskPageVo, Pageable pageable){
        Page<TaskExeorg> result = repository.findPage(taskPageVo,pageable);
        return result;
    }

    /**不分页*/
    public List<TaskExeorg> findList(TaskPageVo taskPageVo){
        List<TaskExeorg> result = repository.findList(taskPageVo);
        return result;
    }


















}
