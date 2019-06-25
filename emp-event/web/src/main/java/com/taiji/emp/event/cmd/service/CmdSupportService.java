package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.CmdSupportClient;
import com.taiji.emp.event.cmd.feign.PlanSupportClient;
import com.taiji.emp.event.cmd.feign.SupportClient;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.emp.event.cmd.vo.CmdSupportVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportListVo;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.vo.SupportVo;
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
public class CmdSupportService extends BaseService {

    @Autowired
    private CmdSupportClient client;
    @Autowired
    private SupportClient supportClient;
    @Autowired
    private PlanSupportClient planSupportClient;

    //新增关联社会依托资源
    public void addEcSupports(String schemeId, List<String> supportIds, Principal principal){
        Assert.hasText(schemeId,"schemeId 不能为空");
        SupportListVo supportListVo = new SupportListVo();
        supportListVo.setSupportIds(supportIds);
        ResponseEntity<List<SupportVo>> supportResultList = supportClient.findList(supportListVo);
        List<SupportVo> supportList = ResponseEntityUtils.achieveResponseEntityBody(supportResultList);
        if(null!=supportList && supportList.size()>0){
            List<CmdSupportVo> cmdSupportVos = new ArrayList<>();
            for(SupportVo supportVo:supportList){
                cmdSupportVos.add(supportToCmdSupport(schemeId,supportVo,principal));
            }
            ResponseEntity<List<CmdSupportVo>> result = client.createList(cmdSupportVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }
    }

    //社会依托资源对象转化为应急处置社会依托资源关联对象
    private CmdSupportVo supportToCmdSupport(String schemeId,SupportVo supportVo, Principal principal){
        CmdSupportVo cmdSupportVo = new CmdSupportVo();
        cmdSupportVo.setAddress(supportVo.getAddress());
        cmdSupportVo.setPrincipal(supportVo.getPrincipal());
        cmdSupportVo.setPrincipalTel(supportVo.getPrincipalTel());
        cmdSupportVo.setSchemeId(schemeId);
        cmdSupportVo.setSupportId(supportVo.getId());
        cmdSupportVo.setSupportName(supportVo.getName());
        cmdSupportVo.setSupportSize(supportVo.getSupportSize());
        cmdSupportVo.setSuppTypeName(supportVo.getTypeName());
        cmdSupportVo.setCreateBy(principal.getName());
        cmdSupportVo.setUpdateBy(principal.getName());

        return cmdSupportVo;
    }

    //删除关联的社会依托资源
    public void deleteEcSupport(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的社会依托资源信息
    public List<CmdSupportVo> findSupportsAll(Map<String,Object> map){
        ResponseEntity<List<CmdSupportVo>> result = client.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据启动的预案数字化信息生成关联的社会依托资源信息
    public void addPlanSupports(CmdPlansVo cmdPlansVo, Principal principal){
        String schemeId = cmdPlansVo.getSchemeId();
        Assert.hasText(schemeId,"schemeId 不能为空");

        List<String> planIds = cmdPlansVo.getPlanIds();
        if(planIds.size()<=0){
            return;
        }
        //调用 预案数字化中 根据预案ID数组，查询其对应的所有社会依托资源信息（去重复）-不分页 接口
        PlanSupportListVo planSupportListVo = new PlanSupportListVo();
        planSupportListVo.setPlanIds(planIds);
        ResponseEntity<List<SupportVo>> supportResultList = planSupportClient.findByPlanIds(planSupportListVo);
        List<SupportVo> supportVoList = ResponseEntityUtils.achieveResponseEntityBody(supportResultList);

        if(null!=supportVoList && supportVoList.size()>0){
            List<CmdSupportVo> cmdSupportVos = new ArrayList<>();
            for(SupportVo supportVo:supportVoList){
                cmdSupportVos.add(supportToCmdSupport(schemeId,supportVo,principal));
            }
            ResponseEntity<List<CmdSupportVo>> result = client.createList(cmdSupportVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }
    }

    /**
     * 【2019年1月18日新增】按照地图格式要求获取方案中的社会依托资源信息
     * @param cmdPlansVo
     * @return
     */
    public List<Map<String,Object>> getSupportForGis(CmdPlansVo cmdPlansVo) {
        List<Map<String,Object>> list = new ArrayList<>();
        String schemeId = cmdPlansVo.getSchemeId();
        ResponseEntity<List<SupportVo>> resultVo = supportClient.findBySchemeId(schemeId);
        List<SupportVo> voList = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        if (!CollectionUtils.isEmpty(voList)){
            for (SupportVo vo : voList){
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
