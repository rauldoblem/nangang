package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanSupport;
import com.taiji.emp.res.entity.Support;
import com.taiji.emp.res.repository.PlanSupportRepository;
import com.taiji.emp.res.repository.SupportRepository;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportListVo;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportPageVo;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.vo.PlanSupportVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlanSupportService extends BaseService<PlanSupport,String>{

    @Autowired
    private PlanSupportRepository repository;

    @Autowired
    private SupportRepository supportRepository;

    public PlanSupport createOrUpdate(PlanSupportVo vo){
        Assert.notNull(vo,"PlanSupportVo 对象不能为 null");
        return repository.deleteAfterSave(vo);
    }

    public PlanSupport findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deletePhysical(PlanSupportVo vo){
        Assert.notNull(vo,"PlanSupportVo不能为 null");
        List<PlanSupport> entity = repository.findPlanSupportByParms(vo);
        for (PlanSupport ps:entity) {
            repository.delete(ps);
        }
    }

    //提供给controller使用的 分页list查询方法
    public Page<PlanSupport> findPage(PlanSupportPageVo PlanSupportPageVo, Pageable pageable){
        return repository.findPage(PlanSupportPageVo,pageable);
    }

    //不分页list查询
    public List<PlanSupport> findList(PlanSupportListVo PlanSupportListVo){
        return repository.findList(PlanSupportListVo);
    }

    //不分页list查询去重的专家ids
    public List<Support> findSupportsByPlanIds(PlanSupportListVo planSupportListVo){
        List<String> planIds =  repository.findSupportsByPlanIds(planSupportListVo.getPlanIds());
        if (!CollectionUtils.isEmpty(planIds)){
            SupportListVo supportListVo = new SupportListVo();
            supportListVo.setSupportIds(planIds);
            return supportRepository.findList(supportListVo);
        }else{
            return null;
        }
    }
}
