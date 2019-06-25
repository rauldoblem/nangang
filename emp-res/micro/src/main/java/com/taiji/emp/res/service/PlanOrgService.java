package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanOrg;
import com.taiji.emp.res.repository.PlanOrgRepository;
import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
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
public class PlanOrgService extends BaseService<PlanOrg,String>{

    @Autowired
    private PlanOrgRepository repository;

    public PlanOrg createOrUpdate(PlanOrg entity){
        Assert.notNull(entity,"PlanOrg 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public PlanOrg findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        PlanOrg entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //不分页list查询
    public List<PlanOrg> findList(PlanOrgListVo planOrgListVo){
        return repository.findList(planOrgListVo);
    }

    //不分页list查询
    public List<PlanOrg> findListByPlanIds(PlanOrgListVo planOrgListVo){
        return repository.findList(planOrgListVo);
    }
}
