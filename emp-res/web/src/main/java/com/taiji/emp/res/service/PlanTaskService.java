package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.feign.PlanTaskClient;
import com.taiji.emp.res.searchVo.planTask.PlanTaskListVo;
import com.taiji.emp.res.searchVo.planTask.PlanTaskPageVo;
import com.taiji.emp.res.vo.PlanTaskVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class PlanTaskService extends BaseService {

    @Autowired
    private PlanTaskClient planTaskClient;

    /**
     * 新增预案任务设置管理记录
     */
    public void create(PlanTaskVo vo, Principal principal){

        UserVo userVo = getCurrentUser(principal);

        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        ResponseEntity<PlanTaskVo> resultVo = planTaskClient.create(vo); //预案任务实体入库保存
    }

    /**
     * 更新单条预案任务设置管理记录
     */
    public void update(PlanTaskVo vo, Principal principal,String id){

        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setUpdateBy(userName); //更新人

//        String eventGradeId = vo.getEventGradeId();
//        String princiOrgId = vo.getPrinciOrgId();
//
//        PlanTaskVo tempVo = this.findOne(vo.getId());
//        if(!eventGradeId.equals(tempVo.getEventGradeId())){ //响应级别名称有修改才替换
//            vo.setEventGradeName(getItemById(eventGradeId).getItemName());
//        }else{
//            vo.setEventGradeName(null);
//        }
//
//        if(!princiOrgId.equals(tempVo.getPrinciOrgId())){
//            vo.setPrinciOrgName(getItemById(princiOrgId).getItemName());
//        }else {
//            vo.setPrinciOrgName(null);
//        }

        ResponseEntity<PlanTaskVo> resultVo = planTaskClient.update(vo,id);

    }

    /**
     * 根据id获取单条预案管理基础记录
     */
    public PlanTaskVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanTaskVo> resultVo = planTaskClient.findOne(id);
        PlanTaskVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条预案任务设置管理记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = planTaskClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     *
     * 获取预案任务设置管理记录分页list
     */
    public RestPageImpl<PlanTaskVo> findPage(PlanTaskPageVo planTaskPageVo){
        Assert.notNull(planTaskPageVo,"PlanTaskPageVo 不能为null");
        ResponseEntity<RestPageImpl<PlanTaskVo>> resultVo = planTaskClient.findPage(planTaskPageVo);
        RestPageImpl<PlanTaskVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取预案任务设置管理记录list(不带分页)
     */
    public List<PlanTaskVo> findList(PlanTaskListVo planTaskListVo){
        Assert.notNull(planTaskListVo,"PlanTaskListVo 不能为null");
        ResponseEntity<List<PlanTaskVo>> resultVo = planTaskClient.findList(planTaskListVo);
        List<PlanTaskVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

}
