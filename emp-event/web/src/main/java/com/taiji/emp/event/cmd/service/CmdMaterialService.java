package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.CmdMaterialClient;
import com.taiji.emp.event.cmd.feign.MaterialClient;
import com.taiji.emp.event.cmd.feign.PlanMaterialClient;
import com.taiji.emp.event.cmd.vo.CmdMaterialVo;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.planMaterial.PlanMaterialListVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmdMaterialService extends BaseService {

    @Autowired
    private CmdMaterialClient client;
    @Autowired
    private MaterialClient materialClient;
    @Autowired
    private PlanMaterialClient planMaterialClient;


    //新增关联应急物资
    public void addEcMaterials(String schemeId, List<String> materialIds, Principal principal){
        Assert.hasText(schemeId,"schemeId 不能为空");
        MaterialListVo materialListVo = new MaterialListVo();
        materialListVo.setMaterialIds(materialIds);
        ResponseEntity<List<MaterialVo>> materialResultList = materialClient.findList(materialListVo);
        List<MaterialVo> materialList = ResponseEntityUtils.achieveResponseEntityBody(materialResultList);
        if(null!=materialList && materialList.size()>0){
            List<CmdMaterialVo> cmdMaterialVos = new ArrayList<>();
            for(MaterialVo materialVo:materialList){
                cmdMaterialVos.add(materialToCmdMaterial(schemeId,materialVo,principal));
            }
            ResponseEntity<List<CmdMaterialVo>> result = client.createList(cmdMaterialVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }
    }

    //应急物资对象转化为应急处置应急物资关联对象
    private CmdMaterialVo materialToCmdMaterial(String schemeId,MaterialVo materialVo, Principal principal){
        CmdMaterialVo cmdMaterialVo = new CmdMaterialVo();
        cmdMaterialVo.setMaterialId(materialVo.getId());
        cmdMaterialVo.setMaterialName(materialVo.getName());
        cmdMaterialVo.setRepertoryId(materialVo.getRepertoryId());
        cmdMaterialVo.setRepertoryName(materialVo.getRepertoryName());
        cmdMaterialVo.setRemainingQuantity(materialVo.getRemainingQuantity());
        cmdMaterialVo.setResTypeName(materialVo.getResTypeName());
        cmdMaterialVo.setSchemeId(schemeId);
        cmdMaterialVo.setUnit(materialVo.getUnit());
        cmdMaterialVo.setUnitMeasure(materialVo.getUnitMeasure());
        cmdMaterialVo.setCreateBy(principal.getName());
        cmdMaterialVo.setUpdateBy(principal.getName());
        cmdMaterialVo.setSpecModel(materialVo.getSpecModel());
        return cmdMaterialVo;
    }

    //删除关联的应急物资
    public void deleteEcMaterial(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的应急物资信息
    public List<CmdMaterialVo> findMaterialsAll(Map<String,Object> map){
        ResponseEntity<List<CmdMaterialVo>> result = client.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据启动的预案数字化信息生成关联的应急物资信息
    public void addPlanMaterials(CmdPlansVo cmdPlansVo, Principal principal){
        String schemeId = cmdPlansVo.getSchemeId();
        Assert.hasText(schemeId,"schemeId 不能为空");

        List<String> planIds = cmdPlansVo.getPlanIds();
        if(planIds.size()<=0){
            return;
        }
        //调用 预案数字化中 根据预案ID数组，查询其对应的所有应急物资信息（去重复）-不分页 接口
        PlanMaterialListVo planMaterialListVo = new PlanMaterialListVo();
        planMaterialListVo.setPlanIds(planIds);
        ResponseEntity<List<MaterialVo>> materialResultList = planMaterialClient.findByPlanIds(planMaterialListVo);
        List<MaterialVo> materialVoList = ResponseEntityUtils.achieveResponseEntityBody(materialResultList);

        if(null!=materialVoList && materialVoList.size()>0){
            List<CmdMaterialVo> cmdMaterialVos = new ArrayList<>();
            for(MaterialVo materialVo:materialVoList){
                cmdMaterialVos.add(materialToCmdMaterial(schemeId,materialVo,principal));
            }
            ResponseEntity<List<CmdMaterialVo>> result = client.createList(cmdMaterialVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }
    }

    /**
     * 【2019年1月18日新增】按照地图格式要求获取方案中的物资信息
     * @param cmdPlansVo
     * @return
     */
    public List<Map<String,Object>> getMaterialsForGis(CmdPlansVo cmdPlansVo) {
        List<Map<String,Object>> list = new ArrayList<>();
        String schemeId = cmdPlansVo.getSchemeId();
        ResponseEntity<List<MaterialVo>> resultVo = materialClient.findBySchemeId(schemeId);
        List<MaterialVo> voList = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if (!CollectionUtils.isEmpty(voList)){
            for (MaterialVo vo : voList){
                Map<String,Object> map = new HashMap<>();
                Map<String,Object> geometryMap = new HashMap<>();
                geometryMap.put("type","Point");
                Double[] coordinatesArray = getCoordinates(vo.getLonAndLat());
                if(null==coordinatesArray){
                    continue; //经纬度解析结果为 null，则跳出循环
                }
                geometryMap.put("coordinates",coordinatesArray);
                map.put("properties",vo);
                map.put("type","Feature");
                map.put("geometry",geometryMap);
                list.add(map);
            }
        }
        return list;
    }
}
