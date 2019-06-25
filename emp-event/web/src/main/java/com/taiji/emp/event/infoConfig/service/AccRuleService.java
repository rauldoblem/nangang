package com.taiji.emp.event.infoConfig.service;

import com.taiji.emp.event.infoConfig.feign.AcceptInformClient;
import com.taiji.emp.event.infoConfig.feign.AcceptRuleClient;
import com.taiji.emp.event.infoConfig.vo.AcceptRuleVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service
public class AccRuleService extends BaseService{

    @Autowired
    private AcceptRuleClient client;

    //创建接报要求
    public void create(AcceptRuleVo vo){
        Assert.notNull(vo,"vo不能为null");
        ResponseEntity<AcceptRuleVo> resultVo = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //更新接报要求
    public void update(AcceptRuleVo vo,String id){
        Assert.notNull(vo,"vo不能为null");
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<AcceptRuleVo> resultVo = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //根据查询条件查询接报要求
    //key:eventTypeId
    public AcceptRuleVo getRuleSetting(Map<String,Object> params){
        ResponseEntity<AcceptRuleVo> resultVo = client.getRuleSetting(convertMap2MultiValueMap(params));
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

}
