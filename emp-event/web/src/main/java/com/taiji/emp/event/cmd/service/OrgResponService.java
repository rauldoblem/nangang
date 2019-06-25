package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.OrgResponClient;
import com.taiji.emp.event.cmd.feign.PlanOrgResponClient;
import com.taiji.emp.event.cmd.vo.CmdOrgVo;
import com.taiji.emp.event.cmd.vo.OrgResponDetailVo;
import com.taiji.emp.event.cmd.vo.OrgResponVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.emp.res.vo.PlanOrgResponVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrgResponService extends BaseService {

    @Autowired
    private OrgResponClient orgResponClient;
    @Autowired
    private PlanOrgResponClient planOrgResponClient;

    //新增应急责任单位/人
    public void addEcOrgRespon(OrgResponVo vo, Principal principal){
        Assert.notNull(vo,"vo 不能为null");
        vo.setCreateBy(principal.getName());
        vo.setUpdateBy(principal.getName());
        ResponseEntity<OrgResponVo> resultVo = orgResponClient.createOne(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }


    //获取单条责任单位/人信息
    public OrgResponVo  findEcOrgResponById(String id){
        Assert.hasText(id,"id 不能是空字符串");
        ResponseEntity<OrgResponVo> resultVo = orgResponClient.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }


    //修改责任单位/人
    public void updateEcOrgRespon(OrgResponVo vo,String id, Principal principal){
        Assert.hasText(id,"id 不能是空字符串");
        Assert.notNull(vo,"vo 不能为null");
        vo.setUpdateBy(principal.getName());
        ResponseEntity<OrgResponVo> resultVo = orgResponClient.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //删除责任单位/人
    public void deleteEcOrgRespon(String id){
        Assert.hasText(id,"id 不能是空字符串");
        ResponseEntity<Void> resultVo = orgResponClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }


    //根据条件查询责任单位/人信息
    public List<OrgResponVo> findOrgResponsAll(Map<String,Object> map){
        ResponseEntity<List<OrgResponVo>> resultVoList = orgResponClient.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(resultVoList);
    }

    //根据启动的预案数字化信息生成关联的责任单位/人信息
    public void addPlanEmgResponses(List<CmdOrgVo> orgList, Principal principal){

        String account = principal.getName();

        Map<String,String> cmdOrgPlanOrgIdMap = orgList.stream().collect(Collectors.toMap(CmdOrgVo::getPlanOrgId, CmdOrgVo::getId));//用于记录(planOrgId,cmdOrgId)

        List<String> planOrgIds = orgList.stream().map(temp -> temp.getPlanOrgId()).collect(Collectors.toList()); //作为条件查询数字化责任单位/人记录
        if(planOrgIds.size()<=0){
            return;
        }

        //调用预案数字化 根据预案应急组织ID数组，查询其对应的所有应急组织责任单位/人-不分页 接口
        PlanOrgResponVo researchVo = new PlanOrgResponVo();
        researchVo.setPlanOrgIds(planOrgIds);
        ResponseEntity<List<PlanOrgResponVo>> planOrgResponResult = planOrgResponClient.findByPlanOrgIds(researchVo);
        List<PlanOrgResponVo> planOrgResponVos =ResponseEntityUtils.achieveResponseEntityBody(planOrgResponResult);

        if(null!=planOrgResponVos&&planOrgResponVos.size()>0){
            List<OrgResponVo> orgResponVos = new ArrayList<>();
            for(PlanOrgResponVo planOrgResponVo : planOrgResponVos){
                OrgResponVo orgResponVo = new OrgResponVo();
                orgResponVo.setCreateBy(account);
                orgResponVo.setUpdateBy(account);
                orgResponVo.setDuty(planOrgResponVo.getDuty());
                orgResponVo.setEmgOrgId(cmdOrgPlanOrgIdMap.get(planOrgResponVo.getPlanOrgId())); //应急组织节点
                orgResponVo.setResponsibility(planOrgResponVo.getResponsibility());
                orgResponVo.setEmgOrgName(planOrgResponVo.getPlanOrgName());
                orgResponVo.setSubjectType(planOrgResponVo.getSubjectType());
                orgResponVo.setDetails(planResponDetailsToCmdResponDetails(planOrgResponVo.getDetails()));
                orgResponVo.setPlanResponId(planOrgResponVo.getId());

                orgResponVos.add(orgResponVo);
            }

            ResponseEntity<List<OrgResponVo>> orgResponResult = orgResponClient.createList(orgResponVos);
            ResponseEntityUtils.achieveResponseEntityBody(orgResponResult);
        }
    }

    //预案数字化责任单位/人 List转化为应急处置责任单位/人
    private List<OrgResponDetailVo> planResponDetailsToCmdResponDetails(List<PlanOrgResponDetailVo> planResponDetails){
        if(null==planResponDetails||planResponDetails.size()<=0){
            return null;
        }
        List<OrgResponDetailVo> list = new ArrayList<>();
        for(PlanOrgResponDetailVo planDetailVo : planResponDetails){
            OrgResponDetailVo detailVo = new OrgResponDetailVo();
            detailVo.setPrincipal(planDetailVo.getPrincipal());
            detailVo.setPrincipalTel(planDetailVo.getPrincipalTel());
            detailVo.setRspOrgId(planDetailVo.getRspOrgId());
            detailVo.setRepOrgName(planDetailVo.getRepOrgName());
            detailVo.setPlanResDetailId(planDetailVo.getId());

            list.add(detailVo);
        }
        return list;
    }

}
