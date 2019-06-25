package com.taiji.emp.event.infoConfig.service;

import com.taiji.emp.event.infoConfig.feign.AcceptInformClient;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service
public class AccInfoService extends BaseService{

    @Autowired
    private AcceptInformClient client;

    //新增通知单位
    public void create(AcceptInformVo vo){
        Assert.notNull(vo,"vo不能为null");
        //指令类型ID
        String orderTypeId = vo.getOrderTypeId();
        if (!StringUtils.isBlank(orderTypeId)){
            String orderTypeName = getItemNameById(orderTypeId);
            //指令类型名称
            vo.setOrderTypeName(orderTypeName);
        }
        ResponseEntity<AcceptInformVo> resultVo = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //通过id获取单个通知单位
    public AcceptInformVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<AcceptInformVo> resultVo = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //更新通知单位
    public void update(AcceptInformVo vo,String id){
        Assert.notNull(vo,"vo不能为null");
        Assert.hasText(id,"id不能为空字符串");
        //指令类型ID
        String orderTypeId = vo.getOrderTypeId();
        if (!StringUtils.isBlank(orderTypeId)){
            String orderTypeName = getItemNameById(orderTypeId);
            //指令类型名称
            vo.setOrderTypeName(orderTypeName);
        }
        ResponseEntity<AcceptInformVo> resultVo = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //删除通知单位
    public void delete(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = client.delete(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    //根据条件查询通知单位list
    //key:eventTypeId
    public List<AcceptInformVo> searchAll(Map<String,Object> params){
        ResponseEntity<List<AcceptInformVo>> resultVo = client.searchAll(convertMap2MultiValueMap(params));
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

}
