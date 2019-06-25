package com.taiji.emp.event.infoConfig.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.base.vo.DocEntityVo;
import com.taiji.emp.event.eva.feign.DocAttClient;
import com.taiji.emp.event.infoDispatch.feign.DicItemClient;
import com.taiji.emp.event.infoDispatch.feign.OrgClient;
import com.taiji.emp.event.infoDispatch.feign.UserClient;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:BaseService.java</p >
 * <p>Description: Map转换类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
public class BaseService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private DicItemClient dicItemClient;
    @Autowired
    private OrgClient orgClient;
    @Autowired
    private DocAttClient docAttClient;

    public MultiValueMap<String,Object> convertMap2MultiValueMap( Map<String,Object> sourceM ){
        MultiValueMap<String,Object> multiValueMap = new LinkedMultiValueMap<>();
        for (String key : sourceM.keySet()) {
            Object obj = sourceM.get(key);
            if( obj instanceof List){
                for (Object o : (List)obj) {
                    multiValueMap.add(key,o);
                }
            }else{
                multiValueMap.add(key,sourceM.get(key));
            }
        }

        return multiValueMap;
    }

    //获取用户信息
    public UserVo getCurrentUser(Principal principal){

        String userAccount = principal.getName();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("account",userAccount);
        MultiValueMap<String, Object> multiValueMap = convertMap2MultiValueMap(userMap);
        UserVo userVo = userClient.findOne(multiValueMap).getBody();
        return userVo;
    }

    //根据OrgId获取orgVo
    public OrgVo getOrgVoById(String id){
        Assert.hasText(id,"OrgId id不能为空字符串");
        ResponseEntity<OrgVo> resultVo = orgClient.find(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //根据字典项id获取字典项对象 --- 返还带有 dicItemName 的vo
    public DicGroupItemVo getItemById(String id){
        Assert.hasText(id,"dicItem id不能为空字符串");
        ResponseEntity<DicGroupItemVo> resultVo = dicItemClient.find(id);
        DicGroupItemVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    //根据字典项id获取字典项名称
    public String getItemNameById(String id){
        Assert.hasText(id,"dicItem id不能为空字符串");
        ResponseEntity<String> result = dicItemClient.findItemNameById(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //ids:使用英文逗号连接的 字典项id
    //返回使用英文逗号连接字典项名称
    public String getItemNamesByIds(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        if(null!=idList){
            ResponseEntity<String> result = dicItemClient.findItemNamesByIds(idList);
            return ResponseEntityUtils.achieveResponseEntityBody(result);
        }else {
            return null;
        }
    }

    //附件上传
    public void fileSave(String entityId, List<String> fileIds, List<String> fileDeleteIds) throws RuntimeException{
        ResponseEntity<Void> docResult = docAttClient.saveDocEntity(new DocEntityVo(entityId, fileIds, fileDeleteIds));
        ResponseEntityUtils.achieveResponseEntityBody(docResult);
    }


    //经纬度字符串转数组
    public Double[] getCoordinates(String coordinates){
        if(!StringUtils.isEmpty(coordinates)&&coordinates.contains(",")){
            String a[] =coordinates.split(",");
            Double.valueOf(a[0]);
            Double.valueOf(a[1]);
            Double[] result = {Double.valueOf(a[0]),Double.valueOf(a[1])};
            return result;
        }else{
            return null;
        }
    }
}
