package com.taiji.emp.res.service;

import com.taiji.emp.res.feign.PlanSupportClient;
import com.taiji.emp.res.feign.SupportClient;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportListVo;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportPageVo;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.support.SupportPageVo;
import com.taiji.emp.res.vo.PlanSupportVo;
import com.taiji.emp.res.vo.SupportVo;
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
public class PlanSupportService extends BaseService{

    @Autowired
    private PlanSupportClient planSupportClient;

    @Autowired
    private SupportClient supportClient;
    //新增预案社会依托资源管理
    public void create(PlanSupportVo vo, Principal principal){

//        String eventGradeID = vo.getEventGradeID();
//        Assert.hasText(eventGradeID,"eventGradeID 不能为空");
//        vo.setEventGradeName(getItemNamesByIds(eventGradeID));

        ResponseEntity<PlanSupportVo> resultVo = planSupportClient.create(vo);

    }

    //修改预案社会依托资源管理
    public void update(PlanSupportVo vo, Principal principal,String id){

//        String eventGradeID = vo.getEventGradeId();
//        Assert.hasText(eventGradeID,"eventGradeID 不能为空");
//        PlanSupportVo tempVo = findOne(vo.getId());
//        if(!eventGradeID.equals(tempVo.getEventGradeId())){ //有修改才更新name
//            vo.setEventGradeName(getItemNamesByIds(eventGradeID));
//        }else{
//            vo.setEventGradeName(null);
//        }

        ResponseEntity<PlanSupportVo> resultVo = planSupportClient.update(vo,id);

    }

    /**
     * 根据id获取单条预案社会依托资源管理
     */
    public PlanSupportVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanSupportVo> resultVo = planSupportClient.findOne(id);
        PlanSupportVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deletePhysical(PlanSupportVo vo){
        Assert.notNull(vo,"PlanSupportVo不能为 null");
        ResponseEntity<Void> resultVo = planSupportClient.deletePhysical(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取应急社会依托资源管理分页list
     */
    public RestPageImpl<SupportVo> findPage(PlanSupportPageVo planSupportPageVo){
        Assert.notNull(planSupportPageVo,"planSupportPageVo 不能为空");
        PlanSupportListVo vo = new PlanSupportListVo();
        vo.setEventGradeId(planSupportPageVo.getEventGradeId());
        vo.setPlanId(planSupportPageVo.getPlanId());
        SupportPageVo supportPageVo = new SupportPageVo();
        supportPageVo.setPage(planSupportPageVo.getPage());
        supportPageVo.setSize(planSupportPageVo.getSize());
        ResponseEntity<List<PlanSupportVo>> planSupportVo = planSupportClient.findList(vo);
        List<PlanSupportVo> planSupport = ResponseEntityUtils.achieveResponseEntityBody(planSupportVo);
        if (!CollectionUtils.isEmpty(planSupport)){
            List<String> str = new ArrayList<String>();
            for (PlanSupportVo ps: planSupport) {
                str.add(ps.getSupportId());
            }
            supportPageVo.setSupportIds(str);
            ResponseEntity<RestPageImpl<SupportVo>> resultVo = supportClient.findPage(supportPageVo);
            RestPageImpl<SupportVo> supportVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            return supportVo;
        }else{
            RestPageImpl<SupportVo> supportVos = new RestPageImpl<SupportVo>();
            return supportVos;
        }

    }

    /**
     * 获取预案社会依托资源管理list(不带分页)
     */
    public List<SupportVo> findList(PlanSupportListVo planSupportListVo){
        Assert.notNull(planSupportListVo,"planSupportListVo 不能为空");
        SupportListVo supportListVo = new SupportListVo();
        ResponseEntity<List<PlanSupportVo>> planSupportVo = planSupportClient.findList(planSupportListVo);
        List<PlanSupportVo> planSupport = ResponseEntityUtils.achieveResponseEntityBody(planSupportVo);
        if (!CollectionUtils.isEmpty(planSupport)){
            List<String> str = new ArrayList<String>();
            for (PlanSupportVo ps: planSupport) {
                str.add(ps.getSupportId());
            }
            supportListVo.setSupportIds(str);
            ResponseEntity<List<SupportVo>> resultVo = supportClient.findList(supportListVo);
            List<SupportVo>  supportVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            return supportVo;
        }else{
            List<SupportVo>  supportVos = new ArrayList<SupportVo>();
            return supportVos;
        }

    }

    /**
     * 获取预案社会依托资源管理list(不带分页)去重
     */
    public List<SupportVo> findSupportsByPlanIds(PlanSupportListVo planSupportListVo){
        Assert.notNull(planSupportListVo,"planSupportListVo 不能为空");
        SupportListVo supportListVo = new SupportListVo();
        ResponseEntity<List<PlanSupportVo>> planSupportVo = planSupportClient.findList(planSupportListVo);
        List<PlanSupportVo> planSupport = ResponseEntityUtils.achieveResponseEntityBody(planSupportVo);
        List<String> str = new ArrayList<String>();
        for (PlanSupportVo ps: planSupport) {
            str.add(ps.getSupportId());
        }
        Set h = new HashSet(str);
        str.clear();
        str.addAll(h);
        supportListVo.setSupportIds(str);
        ResponseEntity<List<SupportVo>> resultVo = supportClient.findList(supportListVo);
        List<SupportVo>  supportVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return supportVo;
    }

}
