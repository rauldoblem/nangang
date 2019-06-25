package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.Analyse;
import com.taiji.emp.event.cmd.repository.AnalyseRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AnalyseService extends BaseService<Analyse,String> {

    @Autowired
    private AnalyseRepository repository;

    //应急处置--事件研判信息新增
    public Analyse create(Analyse entity){
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    //应急处置--事件研判信息编辑
    public Analyse update(Analyse entity,String id){
        Assert.hasText(id,"id 不能为空字符串");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    //根据id获取单条应急处置--事件研判信息
    public Analyse findOne(String id){
        Assert.hasText(id,"id 不能为空字符串");
        return repository.findOne(id);
    }

    //逻辑删除事件研判信息
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        Analyse entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    //查询事件研判信息list
    public List<Analyse> findList(MultiValueMap<String,Object> params){
        return repository.findList(params);
    }

}
