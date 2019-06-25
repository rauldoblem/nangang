package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.res.feign.DocAttClient;
import com.taiji.emp.res.feign.PlanBaseClient;
import com.taiji.emp.res.searchVo.planBase.PlanBaseListVo;
import com.taiji.emp.res.searchVo.planBase.PlanBasePageVo;
import com.taiji.emp.res.vo.PlanBaseSaveVo;
import com.taiji.emp.res.vo.PlanBaseVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;

@Service
public class PlanBaseService extends BaseService {

    @Autowired
    private PlanBaseClient planBaseClient;
    @Autowired
    private DocAttClient docAttClient;

    /**
     * 新增预案管理基础记录
     */
    public void create(PlanBaseSaveVo planBaseSaveVo, Principal principal){

        PlanBaseVo vo = planBaseSaveVo.getPlan();

        UserVo userVo = getCurrentUser(principal);

        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setCreateBy(userName); //创建人
        vo.setUpdateBy(userName); //更新人

        String planTypeId = vo.getPlanTypeId();
        String planStatusId = vo.getPlanStatusId();

        Assert.hasText(planTypeId,"预案类型ID 不能为空字符串");
        Assert.hasText(planTypeId,"预案类型状态ID 不能为空字符串");

        vo.setPlanTypeName(getItemById(planTypeId).getItemName());
        vo.setPlanStatusName(getItemById(planStatusId).getItemName());

        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        ResponseEntity<PlanBaseVo> resultVo = planBaseClient.create(vo); //知识实体入库保存

        if(HttpStatus.OK.equals(resultVo.getStatusCode())){
            String entityId = resultVo.getBody().getId();
            List<String> docAttIds = planBaseSaveVo.getFileIds();
            List<String> docAttDelIds = planBaseSaveVo.getFileDeleteIds();
            ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
            if(!HttpStatus.OK.equals(docResult.getStatusCode())){
                throw new RuntimeException();
            }
        }else{
            throw new RuntimeException();
        }

    }

    /**
     * 更新单条预案管理基础记录
     */
    public void update(PlanBaseSaveVo planBaseSaveVo, Principal principal,String id){
        PlanBaseVo vo = planBaseSaveVo.getPlan();
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String userName  = userProfileVo.getName(); //获取用户姓名

        vo.setUpdateBy(userName); //更新人

        String planTypeId = vo.getPlanTypeId();
        String planStatusId = vo.getPlanStatusId();

        Assert.hasText(planTypeId,"预案类型ID 不能为空字符串");
        Assert.hasText(planTypeId,"预案类型状态ID 不能为空字符串");

        PlanBaseVo tempVo = this.findOne(vo.getId());
        if(!planTypeId.equals(tempVo.getPlanTypeId())){ //预案类型有修改才替换
            vo.setPlanTypeName(getItemById(planTypeId).getItemName());
        }else{
            vo.setPlanTypeName(null);
        }

        if(!planStatusId.equals(tempVo.getPlanStatusId())){
            vo.setPlanStatusName(getItemById(planStatusId).getItemName());
        }else {
            vo.setPlanStatusName(null);
        }

        ResponseEntity<PlanBaseVo> resultVo = planBaseClient.update(vo,id);

        if(HttpStatus.OK.equals(resultVo.getStatusCode())){
            List<String> docAttIds = planBaseSaveVo.getFileIds();
            List<String> docAttDelIds = planBaseSaveVo.getFileDeleteIds();
            ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(vo.getId(),docAttIds,docAttDelIds));
            if(!HttpStatus.OK.equals(docResult.getStatusCode())){
                throw new RuntimeException();
            }
        }else{
            throw new RuntimeException();
        }

    }

    /**
     * 根据id获取单条预案管理基础记录
     */
    public PlanBaseVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanBaseVo> resultVo = planBaseClient.findOne(id);
        PlanBaseVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条预案管理基础记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = planBaseClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     *
     * 获取预案管理基础记录分页list
     */
    public RestPageImpl<PlanBaseVo> findPage(PlanBasePageVo planBasePageVo){
        Assert.notNull(planBasePageVo,"PlanBasePageVo 不能为null");
        ResponseEntity<RestPageImpl<PlanBaseVo>> resultVo = planBaseClient.findPage(planBasePageVo);
        RestPageImpl<PlanBaseVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取预案管理基础记录list(不带分页)
     */
    public List<PlanBaseVo> findList(PlanBaseListVo planBaseListVo){
        Assert.notNull(planBaseListVo,"PlanBaseListVo 不能为null");
        ResponseEntity<List<PlanBaseVo>> resultVo = planBaseClient.findList(planBaseListVo);
        List<PlanBaseVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

}
