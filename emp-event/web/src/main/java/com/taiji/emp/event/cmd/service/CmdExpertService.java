package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.CmdExpertClient;
import com.taiji.emp.event.cmd.feign.ExpertClient;
import com.taiji.emp.event.cmd.feign.PlanExpertClient;
import com.taiji.emp.event.cmd.vo.CmdExpertVo;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CmdExpertService extends BaseService {

    @Autowired
    private CmdExpertClient client;
    @Autowired
    private ExpertClient expertClient;
    @Autowired
    private PlanExpertClient planExpertClient;


    //新增关联应急专家
    public void addEcExperts(String schemeId,List<String> expertIds,Principal principal){
        Assert.hasText(schemeId,"schemeId 不能为空");
        ExpertListVo expertListVo = new ExpertListVo();
        expertListVo.setExpertIds(expertIds);
        ResponseEntity<List<ExpertVo>> expertResultList = expertClient.findList(expertListVo);
        List<ExpertVo> expertList = ResponseEntityUtils.achieveResponseEntityBody(expertResultList);
        if(null!=expertList && expertList.size()>0){
            List<CmdExpertVo> cmdExpertVos = new ArrayList<>();
            for(ExpertVo expertVo:expertList){
                cmdExpertVos.add(expertToCmdExpert(schemeId,expertVo,principal));
            }
            ResponseEntity<List<CmdExpertVo>> result = client.createList(cmdExpertVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }
    }

    //应急专家对象转化为应急处置专家关联对象
    private CmdExpertVo expertToCmdExpert(String schemeId,ExpertVo expertVo,Principal principal){
        CmdExpertVo cmdExpertVo = new CmdExpertVo();
        cmdExpertVo.setEventTypeNames(expertVo.getEventTypeNames());
        cmdExpertVo.setExpertId(expertVo.getId());
        cmdExpertVo.setExpertName(expertVo.getName());
        cmdExpertVo.setSchemeId(schemeId);
        cmdExpertVo.setSpecialty(expertVo.getSpecialty());
        cmdExpertVo.setTelephone(expertVo.getTelephone());
        cmdExpertVo.setUnit(expertVo.getUnit());
        cmdExpertVo.setCreateBy(principal.getName());
        cmdExpertVo.setUpdateBy(principal.getName());
        return cmdExpertVo;
    }

    //删除关联的应急专家
    public void deleteEcExpert(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询处置方案已关联的应急专家信息
    public List<CmdExpertVo> findExpertsAll(Map<String,Object> map){
        ResponseEntity<List<CmdExpertVo>> result = client.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据启动的预案数字化信息生成关联的应急专家信息
    public void addPlanExperts(CmdPlansVo cmdPlansVo,Principal principal){
        String schemeId = cmdPlansVo.getSchemeId();
        Assert.hasText(schemeId,"schemeId 不能为空");

        List<String> planIds = cmdPlansVo.getPlanIds();
        if(planIds.size()<=0){
            return;
        }
        //调用 预案数字化中 根据预案ID数组，查询其对应的所有专家信息（去重复）-不分页 接口
        PlanExpertListVo planExpertListVo = new PlanExpertListVo();
        planExpertListVo.setPlanIds(planIds);
        ResponseEntity<List<ExpertVo>> expertResultList = planExpertClient.findByPlanIds(planExpertListVo);
        List<ExpertVo> expertVoList = ResponseEntityUtils.achieveResponseEntityBody(expertResultList);

        if(null!=expertVoList && expertVoList.size()>0){
            List<CmdExpertVo> cmdExpertVos = new ArrayList<>();
            for(ExpertVo expertVo:expertVoList){
                cmdExpertVos.add(expertToCmdExpert(schemeId,expertVo,principal));
            }
            ResponseEntity<List<CmdExpertVo>> result = client.createList(cmdExpertVos);
            ResponseEntityUtils.achieveResponseEntityBody(result);
        }else{
            return;
        }
    }

}
