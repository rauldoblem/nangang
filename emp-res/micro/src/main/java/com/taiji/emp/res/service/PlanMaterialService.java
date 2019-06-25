package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Material;
import com.taiji.emp.res.entity.PlanMaterial;
import com.taiji.emp.res.repository.MaterialRepository;
import com.taiji.emp.res.repository.PlanMaterialRepository;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.planMaterial.PlanMaterialListVo;
import com.taiji.emp.res.vo.PlanMaterialVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlanMaterialService extends BaseService<PlanMaterial,String>{

    @Autowired
    private PlanMaterialRepository repository;

    @Autowired
    private MaterialRepository materialRepository;


    public List<PlanMaterial> create(List<PlanMaterial> voList,String planId){
        Assert.notNull(voList,"voList对象不能为 null");
        List<PlanMaterial> list = repository.deleteAfterSave(voList,planId);
        return list;
    }


    public PlanMaterial findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deletePhysical(PlanMaterialVo vo){
        Assert.notNull(vo,"PlanMaterialVo不能为 null");
        List<PlanMaterial> entity = repository.findPlanMaterialByParms(vo);
        PlanMaterial planMaterial = entity.get(0);
        repository.delete(planMaterial);
    }

    //不分页list查询
    public List<PlanMaterial> findList(PlanMaterialListVo PlanMaterialListVo){
        return repository.findList(PlanMaterialListVo);
    }

    //不分页list查询去重的物资ids
    public List<Material> findMaterialsByPlanIds(PlanMaterialListVo planMaterialListVo){
        List<String> planIds =  repository.findMaterialsByPlanIds(planMaterialListVo.getPlanIds());
        if (!CollectionUtils.isEmpty(planIds)){
            MaterialListVo materialListVo = new MaterialListVo();
            materialListVo.setResTypeIds(planIds);
            return materialRepository.findList(materialListVo);
        }else{
            return null;
        }
    }

}
