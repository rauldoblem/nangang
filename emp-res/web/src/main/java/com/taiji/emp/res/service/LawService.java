package com.taiji.emp.res.service;

import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.res.feign.DocAttClient;
import com.taiji.emp.res.feign.LawClient;
import com.taiji.emp.res.searchVo.law.LawListVo;
import com.taiji.emp.res.searchVo.law.LawPageVo;
import com.taiji.emp.res.vo.LawSaveVo;
import com.taiji.emp.res.vo.LawVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.taiji.base.sys.vo.DicGroupItemVo;
import org.springframework.util.MultiValueMap;

import java.awt.print.Pageable;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LawService extends BaseService{

    @Autowired
    private LawClient lawClient;
    @Autowired
    private DocAttClient docAttClient;

    /**
     * 新增应急知识记录
     */
    public void create(LawSaveVo lawSaveVo, Principal principal){
        LawVo vo = lawSaveVo.getLaw();

        UserVo userVo = getCurrentUser(principal);

        Assert.notNull(userVo, "userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = principal.getName();

        vo.setCreateBy(account);
        vo.setUpdateBy(account);

        String lawTypeId = vo.getLawTypeId();
        String eventTypeId = vo.getEventTypeId();

        Assert.hasText(lawTypeId,"法律法规ID 不能为空字符串");
        Assert.hasText(eventTypeId,"事件类型ID 不能为空字符串");

        DicGroupItemVo entity = getItemById(lawTypeId);
        if(null != entity){
            vo.setLawTypeName(entity.getItemName());
        }

        DicGroupItemVo entityName = getItemById(eventTypeId);
        if (null != entityName) {
            vo.setEventTypeName(entityName.getItemName());
        }
        vo.setCreateOrgCode(userProfileVo.getOrgId());//创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        ResponseEntity<LawVo> resultVo = lawClient.create(vo); //法律实体入库保存

        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = lawSaveVo.getFileIds();
        List<String> docAttDelIds = lawSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }

    /**
     * 更新单条法律法规
     */
    public void update(LawSaveVo lawSaveVo,String id,Principal principal){
        LawVo vo = lawSaveVo.getLaw();
        UserVo userVo = getCurrentUser(principal);
        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = principal.getName(); //获取用户姓名

        vo.setUpdateBy(account); //更新人
        vo.setCreateOrgCode(userProfileVo.getOrgId());
        vo.setCreateOrgName(userProfileVo.getOrgName());

        String lawTypeId = vo.getLawTypeId();
        String eventTypeId = vo.getEventTypeId();

        Assert.hasText(lawTypeId,"法律法规ID，不能为空字符串");
        Assert.hasText(eventTypeId,"事件类型ID，不能为空字符串");
        Assert.hasText(id,"id，不能为空字符串");
        LawVo tempVo = this.findOne(vo.getId());
        if (!lawTypeId.equals(tempVo.getLawTypeId())){
            vo.setLawTypeName((getItemById(lawTypeId).getItemName()));
        }

        if (!eventTypeId.equals(tempVo.getEventTypeId())){
            vo.setEventTypeName(getItemById(eventTypeId).getItemName());
        }

        ResponseEntity<LawVo> resulrVo = lawClient.update(vo,vo.getId());
        ResponseEntityUtils.achieveResponseEntityBody(resulrVo);
        List<String> docAttIds = lawSaveVo.getFileIds();
        List<String> docAttDelIds = lawSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(vo.getId(),docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }


    /**
     * 根据Id获取单条法律法规记录
     */
    public LawVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<LawVo> resultVo = lawClient.findOne(id);
        LawVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条法律法规记录
     */
    public  void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = lawClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }


    /**
     * 获取法律法规分页list
     */
    public RestPageImpl<LawVo> findPage(LawPageVo lawPageVo){
        Assert.notNull(lawPageVo,"lawPageVo 不能为空");
        ResponseEntity<RestPageImpl<LawVo>> resultVo = lawClient.findPage(lawPageVo);
        RestPageImpl<LawVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取法律法规list(不带分页)
     */
    public List<LawVo> findList(LawListVo lawListVo){
        Assert.notNull(lawListVo,"lawListVo 不能为空");
        ResponseEntity<List<LawVo>> resultVo = lawClient.findList(lawListVo);
        List<LawVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }
}