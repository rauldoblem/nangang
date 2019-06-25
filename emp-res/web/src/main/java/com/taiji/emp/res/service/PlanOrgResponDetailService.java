package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.feign.PlanOrgResponDetailClient;
import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class PlanOrgResponDetailService extends BaseService{

    @Autowired
    private PlanOrgResponDetailClient planOrgResponDetailClient;

    //新增预案责任人、单位详情管理
    public void create(PlanOrgResponDetailListVo vo, Principal principal){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        List<PlanOrgResponDetailVo> detailVo = vo.getPlanOrgResponDetailVo();
        for (PlanOrgResponDetailVo planOrgResponDetail:detailVo) {
            planOrgResponDetail.setCreateBy(vo.getCreateBy()); //创建人
            planOrgResponDetail.setUpdateBy(vo.getUpdateBy()); //更新人
        }
        ResponseEntity<PlanOrgResponDetailVo> resultVo = planOrgResponDetailClient.create(vo);

    }

    //修改预案责任人、单位详情管理
    public void update(PlanOrgResponDetailListVo vo, Principal principal,String id){
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        //vo.setUpdateBy(userName); //更新人
        planOrgResponDetailClient.updateDetail(vo,id);

    }

    /**
     * 根据id获取单条预案责任人、单位详情管理
     */
    public PlanOrgResponDetailVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanOrgResponDetailVo> resultVo = planOrgResponDetailClient.findOne(id);
        PlanOrgResponDetailVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
//        ResponseEntity<Void> resultVo = planOrgResponDetailClient.deleteLogic(id);
//        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取预案责任人、单位详情管理list(不带分页)
     */
    public List<PlanOrgResponDetailVo> findList(PlanOrgResponDetailVo planOrgResponDetailVo){
        Assert.notNull(planOrgResponDetailVo,"planOrgResponDetailVo 不能为空");
        ResponseEntity<List<PlanOrgResponDetailVo>> resultVo = planOrgResponDetailClient.findList(planOrgResponDetailVo);
        List<PlanOrgResponDetailVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

}
