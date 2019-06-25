package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.CmdOrgClient;
import com.taiji.emp.event.cmd.feign.OrgResponClient;
import com.taiji.emp.event.cmd.feign.PlanOrgClient;
import com.taiji.emp.event.cmd.vo.CmdOrgVo;
import com.taiji.emp.event.cmd.vo.CmdPlansVo;
import com.taiji.emp.event.cmd.vo.OrgResponDetailVo;
import com.taiji.emp.event.cmd.vo.OrgResponVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.emp.res.vo.PlanOrgResponVo;
import com.taiji.emp.res.vo.PlanOrgVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmdOrgService extends BaseService {

    @Autowired
    private CmdOrgClient client;
    @Autowired
    private PlanOrgClient planOrgClient;

    //新增应急组织机构
    public void addEcEmgOrg(CmdOrgVo vo, Principal principal){

        if(null==vo.getOrders()){
            vo.setOrders(9999); //设置默认值
        }

        vo.setCreateBy(principal.getName());
        vo.setUpdateBy(principal.getName());

        ResponseEntity<CmdOrgVo> result = client.createOne(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //获取单条应急组织机构信息
    public CmdOrgVo findEcEmgOrgById(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<CmdOrgVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //修改应急组织机构
    public void updateEcEmgOrg(CmdOrgVo vo, String id,Principal principal){
        Assert.hasText(id,"id不能为空字符串");
        vo.setUpdateBy(principal.getName());
        ResponseEntity<CmdOrgVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //删除应急组织机构（含该机构下关联的责任单位/人员）
    public void deleteEcEmgOrg(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询应急组织机构树信息
    public List<CmdOrgVo> findEmgOrgsAll(Map<String,Object> map){
        ResponseEntity<List<CmdOrgVo>> result = client.findList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据启动的预案数字化信息生成关联的应急组织机构
    public List<CmdOrgVo> addPlanEmgOrgs(CmdPlansVo vo, Principal principal){
        String account = principal.getName();

        String schemeId = vo.getSchemeId();
        Assert.hasText(schemeId,"schemeId不能为空字符串");

        List<String> planIds = vo.getPlanIds();
        if(planIds.size()<=0){
            return new ArrayList<>();
        }
        //---调用 预案数字化中的 根据预案ID数组，查询其对应的所有应急组织信息-不分页 接口
        PlanOrgListVo planOrgListVo = new PlanOrgListVo();
        planOrgListVo.setPlanIds(planIds);
        ResponseEntity<List<PlanOrgVo>> planOrgVoResult = planOrgClient.findListByPlanIds(planOrgListVo);
        List<PlanOrgVo> planOrgVos = ResponseEntityUtils.achieveResponseEntityBody(planOrgVoResult);

        if(null!=planOrgVos&&planOrgVos.size()>0){
            List<CmdOrgVo> orgVos = new ArrayList<>();
            for(PlanOrgVo planOrgVo : planOrgVos){
                CmdOrgVo orgVo = new CmdOrgVo();
                //预案数字化应急机构信息转化应急处置--应急组织信息
                orgVo.setOrders(planOrgVo.getOrders());
                orgVo.setLeaf(planOrgVo.getLeaf());
                orgVo.setPlanId(planOrgVo.getPlanId());
                orgVo.setSchemeId(schemeId);
                orgVo.setName(planOrgVo.getName());
                orgVo.setParentId(planOrgVo.getParentId());  //micro层处理 id变更问题
                orgVo.setCreateBy(account);
                orgVo.setUpdateBy(account);
                orgVo.setPlanOrgId(planOrgVo.getId()); //记录预案数字化中的应急组织节点id

                orgVos.add(orgVo);
            }

            ResponseEntity<List<CmdOrgVo>> orgResult = client.createList(orgVos);
            return ResponseEntityUtils.achieveResponseEntityBody(orgResult);
        }else{
            return new ArrayList<>();
        }

    }



}
