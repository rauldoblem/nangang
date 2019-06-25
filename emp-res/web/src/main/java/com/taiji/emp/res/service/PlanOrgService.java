package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.feign.PlanOrgClient;
import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.emp.res.vo.PlanOrgVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.List;

@Service
public class PlanOrgService extends BaseService{

    @Autowired
    private PlanOrgClient planOrgClient;

    //新增预案组织机构管理
    public void create(PlanOrgVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人
        if(StringUtils.isEmpty(vo.getOrders())){
            vo.setOrders(1);
        }
//        String eventGradeId = vo.getEventGradeId();
//        Assert.hasText(eventGradeId,"eventGradeId 不能为空");
//        vo.setEventGradeName(getItemNamesByIds(eventGradeId));

        ResponseEntity<PlanOrgVo> resultVo = planOrgClient.create(vo);

    }

    //修改预案组织机构管理
    public void update(PlanOrgVo vo, Principal principal,String id){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setUpdateBy(userName); //更新人
        if(StringUtils.isEmpty(vo.getOrders())){
            vo.setOrders(1);
        }

//        String eventGradeId = vo.getEventGradeId();
//        Assert.hasText(eventGradeId,"eventGradeId 不能为空");
//        PlanOrgVo tempVo = findOne(vo.getId());
//        if(!eventGradeId.equals(tempVo.getEventGradeId())){ //有修改才更新name
//            vo.setEventGradeName(getItemNamesByIds(eventGradeId));
//        }else{
//            vo.setEventGradeName(null);
//        }

        ResponseEntity<PlanOrgVo> resultVo = planOrgClient.update(vo,id);

    }

    /**
     * 根据id获取单条预案组织机构管理
     */
    public PlanOrgVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanOrgVo> resultVo = planOrgClient.findOne(id);
        PlanOrgVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = planOrgClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取预案组织机构管理list(不带分页)
     */
    public List<PlanOrgVo> findList(PlanOrgListVo planOrgListVo){
        Assert.notNull(planOrgListVo,"planOrgListVo 不能为空");
        ResponseEntity<List<PlanOrgVo>> resultVo = planOrgClient.findList(planOrgListVo);
        List<PlanOrgVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据planIds 获取预案组织机构
     */
    public List<PlanOrgVo> findByPlanIds(PlanOrgListVo planOrgListVo){
        Assert.notNull(planOrgListVo,"planOrgListVo 不能为空");
        ResponseEntity<List<PlanOrgVo>> resultVo = planOrgClient.findListByPlanIds(planOrgListVo);
        List<PlanOrgVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

}
