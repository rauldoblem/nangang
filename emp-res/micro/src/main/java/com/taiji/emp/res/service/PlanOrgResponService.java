package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanOrgRespon;
import com.taiji.emp.res.repository.PlanOrgResponRepository;
import com.taiji.emp.res.vo.PlanOrgResponVo;
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
public class PlanOrgResponService extends BaseService<PlanOrgRespon,String>{

    @Autowired
    private PlanOrgResponRepository repository;

    public PlanOrgRespon createOrUpdate(PlanOrgRespon entity){
        Assert.notNull(entity,"PlanOrgRespon 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public PlanOrgRespon findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        PlanOrgRespon entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //不分页list查询
    public List<PlanOrgRespon> findList(PlanOrgResponVo planOrgResponVo){
        return repository.findList(planOrgResponVo);
    }

    //通过planIds不分页list查询
    public List<PlanOrgRespon> findByPlanOrgIds(PlanOrgResponVo planOrgResponVo){
        return repository.findList(planOrgResponVo);
    }
}
