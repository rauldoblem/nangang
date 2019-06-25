package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanBase;
import com.taiji.emp.res.repository.PlanBaseRepository;
import com.taiji.emp.res.searchVo.planBase.PlanBaseListVo;
import com.taiji.emp.res.searchVo.planBase.PlanBasePageVo;
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
public class PlanBaseService extends BaseService<PlanBase,String>{

    @Autowired
    private PlanBaseRepository repository;

    public PlanBase createOrUpdate(PlanBase entity){
        Assert.notNull(entity,"PlanBase 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public PlanBase findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        PlanBase entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<PlanBase> findPage(PlanBasePageVo planBasePageVo, Pageable pageable){
        return repository.findPage(planBasePageVo,pageable);
    }

    //不分页list查询
    public List<PlanBase> findList(PlanBaseListVo planBaseListVo){
        return repository.findList(planBaseListVo);
    }

}
