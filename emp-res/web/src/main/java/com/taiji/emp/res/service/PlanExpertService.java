package com.taiji.emp.res.service;

import com.taiji.emp.res.feign.ExpertClient;
import com.taiji.emp.res.feign.PlanExpertClient;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertPageVo;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.emp.res.vo.PlanExpertVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlanExpertService extends BaseService{

    @Autowired
    private PlanExpertClient planExpertClient;

    @Autowired
    private ExpertClient expertClient;

    //新增预案专家管理
    public void create(PlanExpertVo vo, Principal principal){

//        String eventGradeID = vo.getEventGradeID();
//        Assert.hasText(eventGradeID,"eventGradeID 不能为空");
//        vo.setEventGradeName(getItemNamesByIds(eventGradeID));

        ResponseEntity<PlanExpertVo> resultVo = planExpertClient.create(vo);

    }

    //修改预案专家管理
    public void update(PlanExpertVo vo, Principal principal, String id){

//        String eventGradeID = vo.getEventGradeID();
//        Assert.hasText(eventGradeID,"eventGradeID 不能为空");
//        PlanExpertVo tempVo = findOne(vo.getId());
//        if(!eventGradeID.equals(tempVo.getEventGradeID())){ //有修改才更新name
//            vo.setEventGradeName(getItemNamesByIds(eventGradeID));
//        }else{
//            vo.setEventGradeName(null);
//        }

        ResponseEntity<PlanExpertVo> resultVo = planExpertClient.update(vo,id);

    }

    /**
     * 根据id获取单条预案专家管理
     */
    public PlanExpertVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanExpertVo> resultVo = planExpertClient.findOne(id);
        PlanExpertVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deletePhysical(PlanExpertVo vo){
        Assert.notNull(vo,"PlanExpertVo不能为 null");
        ResponseEntity<Void> resultVo = planExpertClient.deletePhysical(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取应急专家管理分页list
     */
    public RestPageImpl<ExpertVo> findPage(PlanExpertPageVo planExpertPageVo){
        Assert.notNull(planExpertPageVo,"PlanExpertPageVo 不能为空");
        PlanExpertListVo vo = new PlanExpertListVo();
        vo.setEventGradeId(planExpertPageVo.getEventGradeId());
        vo.setPlanId(planExpertPageVo.getPlanId());
        ExpertPageVo expertPageVo = new ExpertPageVo();
        expertPageVo.setPage(planExpertPageVo.getPage());
        expertPageVo.setSize(planExpertPageVo.getSize());
        ResponseEntity<List<PlanExpertVo>> planExpertVo = planExpertClient.findList(vo);
        List<PlanExpertVo> planExpert = ResponseEntityUtils.achieveResponseEntityBody(planExpertVo);
        if(!CollectionUtils.isEmpty(planExpert)){
            List<String> str = new ArrayList<String>();
            for (PlanExpertVo pe: planExpert) {
                str.add(pe.getExpertId());
            }
            expertPageVo.setExpertIds(str);
            ResponseEntity<RestPageImpl<ExpertVo>> resultVo = expertClient.findPage(expertPageVo);
            RestPageImpl<ExpertVo> expertVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            return expertVo;
        }else{
            RestPageImpl<ExpertVo> expertVos = new RestPageImpl<ExpertVo>();
            return expertVos;
        }

    }

    /**
     * 获取预案专家管理list(不带分页)
     */
    public List<ExpertVo> findList(PlanExpertListVo planExpertListVo){
        Assert.notNull(planExpertListVo,"PlanExpertListVo 不能为空");
        ExpertListVo expertListVo = new ExpertListVo();
        ResponseEntity<List<PlanExpertVo>> planExpertVo = planExpertClient.findList(planExpertListVo);
        List<PlanExpertVo> planExpert = ResponseEntityUtils.achieveResponseEntityBody(planExpertVo);
        if(!CollectionUtils.isEmpty(planExpert)){
            List<String> str = new ArrayList<String>();
            for (PlanExpertVo pe: planExpert) {
                str.add(pe.getExpertId());
            }
            expertListVo.setExpertIds(str);
            ResponseEntity<List<ExpertVo>> resultVo = expertClient.findList(expertListVo);
            List<ExpertVo> expertVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            return expertVo;
        }else{
            List<ExpertVo> expertVos = new ArrayList<ExpertVo>();
            return expertVos;
        }

    }

    /**
     * 获取预案专家管理list(不带分页)去重
     */
    public List<ExpertVo> findExpertsByPlanIds(PlanExpertListVo planExpertListVo){
        Assert.notNull(planExpertListVo,"PlanExpertListVo 不能为空");
        ExpertListVo expertListVo = new ExpertListVo();
        ResponseEntity<List<PlanExpertVo>> planExpertVo = planExpertClient.findList(planExpertListVo);
        List<PlanExpertVo> planExpert = ResponseEntityUtils.achieveResponseEntityBody(planExpertVo);
        List<String> str = new ArrayList<String>();
        for (PlanExpertVo pe: planExpert) {
            str.add(pe.getExpertId());
        }
        Set h = new HashSet(str);
        str.clear();
        str.addAll(h);
        expertListVo.setExpertIds(str);
        ResponseEntity<List<ExpertVo>> resultVo = expertClient.findList(expertListVo);
        List<ExpertVo> expertVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return expertVo;
    }

}
