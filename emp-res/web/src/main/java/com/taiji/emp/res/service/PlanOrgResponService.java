package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.feign.PlanOrgResponClient;
import com.taiji.emp.res.feign.PlanOrgResponDetailClient;
import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
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

@Service
public class PlanOrgResponService extends BaseService{

    @Autowired
    private PlanOrgResponClient planOrgResponClient;

    @Autowired
    private PlanOrgResponDetailClient planOrgResponDetailClient;

    //新增预案责任人、单位管理
    public void create(PlanOrgResponVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        ResponseEntity<PlanOrgResponVo> resultVo = planOrgResponClient.create(vo);
        PlanOrgResponVo planOrgResponVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //查询刚入库的一条数据
        /*ResponseEntity<List<PlanOrgResponVo>> planOrgRespons = planOrgResponClient.findList(vo);
        List<PlanOrgResponVo> planOrgRespon = ResponseEntityUtils.achieveResponseEntityBody(planOrgRespons);
        String planOrgResponId = planOrgRespon.get(0).getId();*/
        //获取vo详情数据
        List<PlanOrgResponDetailVo> details = vo.getDetails();
        PlanOrgResponDetailListVo detailsListVo = new PlanOrgResponDetailListVo();
        detailsListVo.setCreateBy(userName); //创建人
        detailsListVo.setUpdateBy(userName); //更新人
        for (PlanOrgResponDetailVo detail:details) {
            detail.setOrgResponId(planOrgResponVo.getId());
            detailsListVo.setPlanOrgResponDetailVo(details);
        }
        //调用详情接口
        planOrgResponDetailClient.create(detailsListVo);
    }

    //修改预案责任人、单位管理
    public void update(PlanOrgResponVo vo,String id, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setUpdateBy(userName); //更新人
        ResponseEntity<PlanOrgResponVo> resultVo = planOrgResponClient.update(vo,id);
        PlanOrgResponVo planOrgResponVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //获取传入数据vo,详情也是传入id
        List<PlanOrgResponDetailVo> planOrgResponDetailVo = vo.getDetails();
        PlanOrgResponDetailListVo detailsListVo = new PlanOrgResponDetailListVo();
        detailsListVo.setCreateBy(userName);
        detailsListVo.setPlanOrgResponDetailVo(planOrgResponDetailVo);
        planOrgResponDetailClient.updateDetail(detailsListVo, id);
    }

    /**
     * 根据id获取单条预案责任人、单位管理
     */
    public PlanOrgResponVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanOrgResponVo> resultVo = planOrgResponClient.findOne(id);
        //获取id调用接口查询详情
        PlanOrgResponDetailVo detail = new PlanOrgResponDetailVo();
        detail.setOrgResponId(id);
        ResponseEntity<List<PlanOrgResponDetailVo>> resultDetailVo = planOrgResponDetailClient.findList(detail);
        List<PlanOrgResponDetailVo> detailVos = ResponseEntityUtils.achieveResponseEntityBody(resultDetailVo);
        PlanOrgResponVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        vo.setDetails(detailVos);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = planOrgResponClient.deleteLogic(id);
        //根据id获取详情数据
        PlanOrgResponDetailVo detail = new PlanOrgResponDetailVo();
        detail.setOrgResponId(id);
        ResponseEntity<List<PlanOrgResponDetailVo>> resultDetailVo = planOrgResponDetailClient.findList(detail);
        List<PlanOrgResponDetailVo> detailVos = ResponseEntityUtils.achieveResponseEntityBody(resultDetailVo);
        List<String> ids = new ArrayList<String>();
        for (PlanOrgResponDetailVo detailVo : detailVos) {
            ids.add(detailVo.getId());
        }
        ResponseEntity<Void> resultDetail = planOrgResponDetailClient.deleteLogic(ids);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取预案责任人、单位管理list(不带分页)
     */
    public List<PlanOrgResponVo> findList(PlanOrgResponVo planOrgResponVo){
        Assert.notNull(planOrgResponVo,"planOrgResponVo 不能为空");
        ResponseEntity<List<PlanOrgResponVo>> resultVo = planOrgResponClient.findList(planOrgResponVo);
        List<PlanOrgResponVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        vo = getDataList(vo);
       /* PlanOrgResponDetailListVo planOrgResponDetailListVo = new PlanOrgResponDetailListVo();
        //根据planOrgId获取详情orgresponId
        //循环添加数据
        for (PlanOrgResponVo planOrgRespon:vo) {
            List<String> ids = new ArrayList<String>();
            ids.add(planOrgRespon.getId());
            planOrgResponDetailListVo.setIds(ids);
            ResponseEntity<List<PlanOrgResponDetailVo>> resultDetailVo = planOrgResponDetailClient.findList(planOrgResponDetailListVo);
            List<PlanOrgResponDetailVo> detailVos = ResponseEntityUtils.achieveResponseEntityBody(resultDetailVo);
            planOrgRespon.setDetails(detailVos);
        }*/
        return vo;
    }

    /**
     * 获取预案责任人、单位管理list(不带分页)
     */
    public List<PlanOrgResponVo> findByPlanOrgIds(PlanOrgResponVo planOrgResponVo){
        Assert.notNull(planOrgResponVo,"planOrgResponVo 不能为空");
        ResponseEntity<List<PlanOrgResponVo>> resultVo = planOrgResponClient.findByPlanOrgIds(planOrgResponVo);
        List<PlanOrgResponVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        vo = getDataList(vo);
        /*PlanOrgResponDetailListVo planOrgResponDetailListVo = new PlanOrgResponDetailListVo();
        for (PlanOrgResponVo planOrgRespon:vo) {
            List<String> ids = new ArrayList<String>();
            ids.add(planOrgRespon.getId());
            planOrgResponDetailListVo.setIds(ids);
            ResponseEntity<List<PlanOrgResponDetailVo>> resultDetailVo = planOrgResponDetailClient.findList(planOrgResponDetailListVo);
            List<PlanOrgResponDetailVo> detailVos = ResponseEntityUtils.achieveResponseEntityBody(resultDetailVo);
            planOrgRespon.setDetails(detailVos);
        }*/
        return vo;
    }

    private List<PlanOrgResponVo> getDataList(List<PlanOrgResponVo> vo){
        PlanOrgResponDetailListVo planOrgResponDetailListVo = new PlanOrgResponDetailListVo();
        for (PlanOrgResponVo planOrgRespon:vo) {
            List<String> ids = new ArrayList<String>();
            ids.add(planOrgRespon.getId());
            planOrgResponDetailListVo.setIds(ids);
            ResponseEntity<List<PlanOrgResponDetailVo>> resultDetailVo = planOrgResponDetailClient.findList(planOrgResponDetailListVo);
            List<PlanOrgResponDetailVo> detailVos = ResponseEntityUtils.achieveResponseEntityBody(resultDetailVo);
            planOrgRespon.setDetails(detailVos);
        }
        return vo;
    }

}
