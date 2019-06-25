package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanCalTree;
import com.taiji.emp.res.repository.PlanCalTreeRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
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
public class PlanCalTreeService extends BaseService<PlanCalTree,String>{

    @Autowired
    private PlanCalTreeRepository repository;

    public PlanCalTree createOrUpdate(PlanCalTree entity){
        Assert.notNull(entity,"PlanCalTree 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public PlanCalTree findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        PlanCalTree entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //不分页list查询
    public List<PlanCalTree> findList(){
        return repository.findList();
    }


}
