package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Expert;
import com.taiji.emp.res.repository.ExpertRepository;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.emp.res.repository.ExpertRepository;
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
public class ExpertService extends BaseService<Expert,String>{

    @Autowired
    private ExpertRepository repository;

    public Expert createOrUpdate(Expert entity){
        Assert.notNull(entity,"Expert 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public Expert findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Expert entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<Expert> findPage(ExpertPageVo expertPageVo, Pageable pageable){
        return repository.findPage(expertPageVo,pageable);
    }

    //不分页list查询
    public List<Expert> findList(ExpertListVo expertListVo){
        return repository.findList(expertListVo);
    }

}
