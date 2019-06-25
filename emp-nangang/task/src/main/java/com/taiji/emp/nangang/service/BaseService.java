package com.taiji.emp.nangang.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.emp.nangang.feign.DicItemClient;
import com.taiji.emp.nangang.feign.OrgClient;
import com.taiji.emp.nangang.feign.UserClient;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;


@Service
public class BaseService {

    @Autowired
    private DicItemClient dicItemClient;
    @Autowired
    private OrgClient orgClient;

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

    //根据dicCode  查询 items集合
    public List<DicGroupItemVo> findItems() {

        Map<String, Object> map = new HashMap<>();
        map.put("dicCode","dicCheckItem");
        MultiValueMap<String, Object> multiValueMap = convertMap2MultiValueMap(map);
        ResponseEntity<List<DicGroupItemVo>> list =
                dicItemClient.findList(multiValueMap);
        List<DicGroupItemVo> dicGroupItemVos =
                ResponseEntityUtils.achieveResponseEntityBody(list);
        return dicGroupItemVos;
    }

    //根据OrgId获取orgVo
    public OrgVo getOrgVoById(String id){
        Assert.hasText(id,"OrgId id不能为空字符串");
        ResponseEntity<OrgVo> resultVo = orgClient.find(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 流式处理去重自定义方法
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
