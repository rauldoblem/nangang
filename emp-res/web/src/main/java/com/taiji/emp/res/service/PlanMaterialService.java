package com.taiji.emp.res.service;

import com.taiji.emp.res.feign.MaterialClient;
import com.taiji.emp.res.feign.PlanMaterialClient;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.planMaterial.PlanMaterialListVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.res.vo.PlanMaterialSaveVo;
import com.taiji.emp.res.vo.PlanMaterialVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlanMaterialService extends BaseService{

    @Autowired
    private PlanMaterialClient planMaterialClient;

    @Autowired
    private MaterialClient materialClient;

    //新增预案物资管理
    public void create(PlanMaterialSaveVo planMaterialSaveVo, Principal principal){
        Assert.notNull(planMaterialSaveVo,"planMaterialSaveVo不能为空");
        List<PlanMaterialVo> materialList = planMaterialSaveVo.getMaterialList();
        if (null != materialList && materialList.size() > 0){
            for (PlanMaterialVo materialVo : materialList){
                materialVo.setPlanId(planMaterialSaveVo.getPlanId());
                materialVo.setEventGradeId(planMaterialSaveVo.getEventGradeId());
                materialVo.setEventGradeName(planMaterialSaveVo.getEventGradeName());
            }
        }
        planMaterialClient.create(materialList,planMaterialSaveVo.getPlanId());
    }

    //修改预案物资管理
    public void update(PlanMaterialVo vo, Principal principal,String id){

//        String eventGradeID = vo.getEventGradeId();
//        Assert.hasText(eventGradeID,"eventGradeID 不能为空");
//        PlanMaterialVo tempVo = findOne(vo.getId());
//        if(!eventGradeID.equals(tempVo.getEventGradeId())){ //有修改才更新name
//            vo.setEventGradeName(getItemNamesByIds(eventGradeID));
//        }else{
//            vo.setEventGradeName(null);
//        }

        ResponseEntity<PlanMaterialVo> resultVo = planMaterialClient.update(vo,id);

    }

    /**
     * 根据id获取单条预案物资管理
     */
    public PlanMaterialVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanMaterialVo> resultVo = planMaterialClient.findOne(id);
        PlanMaterialVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deletePhysical(PlanMaterialVo vo){
        Assert.notNull(vo,"PlanMaterialVo不能为 null");
        ResponseEntity<Void> resultVo = planMaterialClient.deletePhysical(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取预案物资管理list(不带分页)
     */
    public List<PlanMaterialVo> findList(PlanMaterialListVo planMaterialListVo){
        Assert.notNull(planMaterialListVo,"PlanMaterialListVo 不能为空");
//        MaterialListVo materialListVo = new MaterialListVo();
        ResponseEntity<List<PlanMaterialVo>> planMaterialVo = planMaterialClient.findList(planMaterialListVo);
        List<PlanMaterialVo> planMaterial = ResponseEntityUtils.achieveResponseEntityBody(planMaterialVo);
        return planMaterial;
        /*if (!CollectionUtils.isEmpty(planMaterial)){
            List<String> str = new ArrayList<String>();
            for (PlanMaterialVo pm: planMaterial) {
                str.add(pm.getMaterialTypeId());
            }
            materialListVo.setSelectedMaterialIds(str);
            ResponseEntity<List<MaterialVo>> resultVo = materialClient.findList(materialListVo);
            List<MaterialVo> materialVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            return materialVo;
        }else{
            List<MaterialVo> materialVos = new ArrayList<MaterialVo>();
            return materialVos;
        }*/

    }

    /**
     * 获取预案物资管理list(不带分页)
     */
    public List<MaterialVo> findMaterialsByPlanIds(PlanMaterialListVo planMaterialListVo){
        Assert.notNull(planMaterialListVo,"PlanMaterialListVo 不能为空");
        MaterialListVo materialListVo = new MaterialListVo();
        ResponseEntity<List<PlanMaterialVo>> planMaterialVo = planMaterialClient.findList(planMaterialListVo);
        List<PlanMaterialVo> planMaterial = ResponseEntityUtils.achieveResponseEntityBody(planMaterialVo);
        List<String> str = new ArrayList<String>();
        for (PlanMaterialVo pm: planMaterial) {
            str.add(pm.getMaterialTypeId());
        }
        Set h = new HashSet(str);
        str.clear();
        str.addAll(h);
        materialListVo.setMaterialIds(str);
        ResponseEntity<List<MaterialVo>> resultVo = materialClient.findList(materialListVo);
        List<MaterialVo> materialVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return materialVo;
    }

}
