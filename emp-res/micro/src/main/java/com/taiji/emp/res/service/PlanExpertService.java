package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Expert;
import com.taiji.emp.res.entity.PlanExpert;
import com.taiji.emp.res.repository.ExpertRepository;
import com.taiji.emp.res.repository.PlanExpertRepository;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertPageVo;
import com.taiji.emp.res.vo.PlanExpertVo;
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
public class PlanExpertService extends BaseService<PlanExpert,String>{

    @Autowired
    private PlanExpertRepository repository;

    @Autowired
    private ExpertRepository expertRepository;

    public PlanExpert createOrUpdate(PlanExpertVo vo){
        Assert.notNull(vo,"PlanExpertVo 对象不能为 null");
        return repository.deleteAfterSave(vo);
    }

    public PlanExpert findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deletePhysical(PlanExpertVo vo){
        Assert.notNull(vo,"PlanExpertVo不能为 null");
        List<PlanExpert> entity = repository.findPlanExpertByParms(vo);
        for (PlanExpert pe:entity) {
            repository.delete(pe);
        }
    }

    //提供给controller使用的 分页list查询方法
    public Page<PlanExpert> findPage(PlanExpertPageVo planExpertPageVo, Pageable pageable){
        return repository.findPage(planExpertPageVo,pageable);
    }

    //不分页list查询
    public List<PlanExpert> findList(PlanExpertListVo planExpertListVo){
        return repository.findList(planExpertListVo);
    }

    //不分页list查询去重的专家ids
    public List<Expert> findExpertsByPlanIds(PlanExpertListVo planExpertListVo){
        List<String> planIds =  repository.findExpertsByPlanIds(planExpertListVo.getPlanIds());
        if (!CollectionUtils.isEmpty(planIds)){
            ExpertListVo expertListVo = new ExpertListVo();
            expertListVo.setExpertIds(planIds);
            return expertRepository.findList(expertListVo);
        }else{
            return null;
        }
    }

}
