package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.feign.SchemeClient;
import com.taiji.emp.event.cmd.vo.SchemeVo;
import com.taiji.emp.event.infoConfig.service.BaseService;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.Map;

@Service
public class SchemeService extends BaseService {

    @Autowired
    SchemeClient client;

    //启动预案，生成处置方案基本信息，处置方案名称默认为‘事件名称+处置方案
    public SchemeVo addEcScheme(Map<String,Object> map, Principal principal){

        SchemeVo schemeVo = new SchemeVo();
        if(map.containsKey("eventId")){
            String eventId = map.get("eventId").toString();
            Assert.hasText(eventId,"eventId不能为空");
            schemeVo.setEventId(eventId);
        }

        String eventName = "";
        if(map.containsKey("eventName")){
            eventName = map.get("eventName").toString();
            Assert.hasText(eventName,"eventName不能为空");
        }

        schemeVo.setSchemeName(eventName+"处置方案");
        schemeVo.setCreateBy(principal.getName());
        schemeVo.setUpdateBy(principal.getName());

        ResponseEntity<SchemeVo> resultVo = client.create(schemeVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);

    }

    //根据事件ID获取事件处置方案基本信息
    public SchemeVo  findSchemeVoByEventId(String eventId){
        Assert.hasText(eventId,"eventId不能为空");
        ResponseEntity<SchemeVo> resultVo = client.findByEventId(eventId);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);

    }

    //修改处置方案
    public SchemeVo updateScheme(SchemeVo vo,String id, Principal principal){
        Assert.hasText(id,"id不能为空");
        vo.setUpdateBy(principal.getName());
        ResponseEntity<SchemeVo> resultVo = client.update(vo,id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

}
