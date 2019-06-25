package com.taiji.emp.res.service;

import com.taiji.emp.res.feign.PlanCalTreeClient;
import com.taiji.emp.res.vo.PlanCalTreeVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class PlanCalTreeService extends BaseService {

    @Autowired
    private PlanCalTreeClient planCalTreeClient;

    //新增预案目录
    public void create(PlanCalTreeVo vo){

        ResponseEntity<PlanCalTreeVo> resultVo = planCalTreeClient.create(vo);

    }

    //修改预案目录
    public void update(PlanCalTreeVo vo,String id){

        ResponseEntity<PlanCalTreeVo> resultVo = planCalTreeClient.update(vo,id);

    }

    /**
     * 根据id获取单条预案目录
     */
    public PlanCalTreeVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanCalTreeVo> resultVo = planCalTreeClient.findOne(id);
        PlanCalTreeVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<Void> resultVo = planCalTreeClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取预案目录list(不带分页)
     */
    public List<PlanCalTreeVo> findList(){
        ResponseEntity<List<PlanCalTreeVo>> resultVo = planCalTreeClient.findList();
        List<PlanCalTreeVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

}
