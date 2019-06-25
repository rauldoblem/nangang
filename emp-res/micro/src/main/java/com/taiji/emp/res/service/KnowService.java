package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Knowledge;
import com.taiji.emp.res.repository.KnowRepository;
import com.taiji.emp.res.searchVo.knowledge.KnowListVo;
import com.taiji.emp.res.searchVo.knowledge.KnowPageVo;
import com.taiji.emp.res.entity.Knowledge;
import com.taiji.emp.res.repository.KnowRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class KnowService extends BaseService<Knowledge,String>{

    @Autowired
    private KnowRepository repository;

    public Knowledge create(Knowledge entity){
        Assert.notNull(entity,"Knowledge 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
//        entity.setCreateTime(LocalDateTime.now());
//        entity.setUpdateTime(LocalDateTime.now());
        Knowledge result = repository.save(entity);
        return result;
    }

    public Knowledge update(Knowledge entity){
        Assert.notNull(entity,"Knowledge 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
//        entity.setUpdateTime(LocalDateTime.now());
        Knowledge result = repository.save(entity);
        return result;
    }

    public Knowledge findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        Knowledge result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Knowledge entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<Knowledge> findPage(KnowPageVo knowPageVo, Pageable pageable){
        Page<Knowledge> result = repository.findPage(knowPageVo,pageable);
        return result;
    }

    public List<Knowledge> findList(KnowListVo knowListVo){
        List<Knowledge> result = repository.findList(knowListVo);
        return result;
    }

}
