package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanTask;
import com.taiji.emp.res.repository.PlanTaskRepository;
import com.taiji.emp.res.searchVo.planTask.PlanTaskListVo;
import com.taiji.emp.res.searchVo.planTask.PlanTaskPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlanTaskService extends BaseService<PlanTask,String>{

    @Autowired
    private PlanTaskRepository repository;

    public PlanTask createOrUpdate(PlanTask entity){
        Assert.notNull(entity,"PlanTask 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public PlanTask findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        PlanTask entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<PlanTask> findPage(PlanTaskPageVo planTaskPageVo, Pageable pageable){
        return repository.findPage(planTaskPageVo,pageable);
    }

    //不分页list查询
    public List<PlanTask> findList(PlanTaskListVo planTaskListVo){
        return repository.findList(planTaskListVo);
    }

}
