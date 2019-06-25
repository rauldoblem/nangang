package com.taiji.emp.res.service;

import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.res.feign.DicItemClient;
import com.taiji.emp.res.feign.DocAttClient;
import com.taiji.emp.res.feign.KnowClient;
import com.taiji.emp.res.feign.UserClient;
import com.taiji.emp.res.searchVo.knowledge.KnowListVo;
import com.taiji.emp.res.searchVo.knowledge.KnowPageVo;
import com.taiji.emp.res.vo.KnowSaveVo;
import com.taiji.emp.res.vo.KnowledgeVo;
import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.res.service.BaseService;
import com.taiji.emp.res.feign.KnowClient;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class    KnowService extends BaseService {

    @Autowired
    private KnowClient knowClient;
    @Autowired
    private DocAttClient docAttClient;

    /**
     * 新增应急知识记录
     */
    public void create(KnowSaveVo knowSaveVo, Principal principal){

        KnowledgeVo vo = knowSaveVo.getKnowledge();

        UserVo userVo = getCurrentUser(principal);

        Assert.notNull(userVo,"userVo不能为null");
        UserProfileVo userProfileVo = userVo.getProfile();
        String account = principal.getName();

        vo.setCreateBy(account); //创建人
        vo.setUpdateBy(account); //更新人

        String knoTypeId = vo.getKnoTypeId();
        String eventTypeId = vo.getEventTypeId();

        Assert.hasText(knoTypeId,"知识类型ID 不能为空字符串");
        Assert.hasText(eventTypeId,"适用事件分类ID 不能为空字符串");

        vo.setKnoTypeName(getItemNameById(knoTypeId));
        vo.setEventTypeName(getItemNameById(eventTypeId));

        vo.setCreateOrgId(userProfileVo.getOrgId()); //创建单位id
        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        ResponseEntity<KnowledgeVo> resultVo = knowClient.create(vo); //知识实体入库保存
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        String entityId = resultVo.getBody().getId();
        List<String> docAttIds = knowSaveVo.getFileIds();
        List<String> docAttDelIds = knowSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId,docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }

    /**
     * 更新单条应急知识记录
     */
    public void update(KnowSaveVo knowSaveVo,String id, Principal principal){
        KnowledgeVo vo = knowSaveVo.getKnowledge();

        String account = principal.getName();

        vo.setUpdateBy(account); //更新人
//        vo.setCreateOrgCode(userProfileVo.getOrgId()); //创建单位id
//        vo.setCreateOrgName(userProfileVo.getOrgName());//创建单位名称

        String knoTypeId = vo.getKnoTypeId();
        String eventTypeId = vo.getEventTypeId();

        Assert.hasText(knoTypeId,"知识类型ID 不能为空字符串");
        Assert.hasText(eventTypeId,"适用事件分类ID 不能为空字符串");

        Assert.hasText(id,"id 不能为空字符串");
        KnowledgeVo tempVo = this.findOne(id);
        if(!knoTypeId.equals(tempVo.getKnoTypeId())){ //知识类型有修改才替换
            vo.setKnoTypeName(getItemNameById(knoTypeId));
        }else{
            vo.setKnoTypeName(null);
        }

        if(!eventTypeId.equals(tempVo.getEventTypeId())){
            vo.setEventTypeName(getItemNameById(eventTypeId));
        }else {
            vo.setEventTypeName(null);
        }

        ResponseEntity<KnowledgeVo> resultVo = knowClient.update(vo,vo.getId());
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        List<String> docAttIds = knowSaveVo.getFileIds();
        List<String> docAttDelIds = knowSaveVo.getFileDeleteIds();
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(vo.getId(),docAttIds,docAttDelIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }

    /**
     * 根据id获取单条应急知识记录
     */
    public KnowledgeVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<KnowledgeVo> resultVo = knowClient.findOne(id);
        KnowledgeVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 根据id逻辑删除单条应急知识记录
     */
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = knowClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     *
     * 获取应急知识分页list
     */
    public RestPageImpl<KnowledgeVo> findPage(KnowPageVo knowPageVo){
        Assert.notNull(knowPageVo,"knowPageVo 不能为null");
        ResponseEntity<RestPageImpl<KnowledgeVo>> resultVo = knowClient.findPage(knowPageVo);
        RestPageImpl<KnowledgeVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    /**
     * 获取应急知识list(不带分页)
     */
    public List<KnowledgeVo> findList(KnowListVo knowListVo){
        Assert.notNull(knowListVo,"knowListVo 不能为null");
        ResponseEntity<List<KnowledgeVo>> resultVo = knowClient.findList(knowListVo);
        List<KnowledgeVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

}
