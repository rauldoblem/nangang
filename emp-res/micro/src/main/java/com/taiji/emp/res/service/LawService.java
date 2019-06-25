package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Law;
import com.taiji.emp.res.repository.LawRepository;
import com.taiji.emp.res.searchVo.law.LawListVo;
import com.taiji.emp.res.searchVo.law.LawPageVo;
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

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class LawService extends BaseService<Law, String> {

    @Autowired
    private LawRepository repository;

    public Law create(Law entity){
        Assert.notNull(entity,"Law对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Law result = repository.save(entity);
        return result;
    }

    public Law update(Law entity){
        Assert.notNull(entity,"law对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Law result = repository.save(entity);
        return result;
    }

    public Law findOne(String id){
        Assert.hasText(id,"id不能为null或字符串！");
        Law result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串！");
        Law entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的分页List查询方法
    public Page<Law> findPage(LawPageVo lawPageVo, Pageable pageable){
        Page<Law> result = repository.findPage(lawPageVo,pageable);
        return result;
    }

    public List<Law> findList(LawListVo lawListVo){
        List<Law> result = repository.findList(lawListVo);
        return result;
    }
}
