package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.CmdExpert;
import com.taiji.emp.event.cmd.repository.CmdExpertRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CmdExpertService extends BaseService<CmdExpert,String> {

    @Autowired
    private CmdExpertRepository repository;

    //新增单个应急专家关联信息
    public CmdExpert createOne(CmdExpert entity){
        Assert.notNull(entity,"CmdExpert 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }
    //新增多个应急专家关联信息
    public List<CmdExpert> createList(List<CmdExpert> entityList){
        Assert.notNull(entityList,"entityList 不能为null");
        if(entityList.size()>0){
            List<CmdExpert> resultList = new ArrayList<>();
            for(CmdExpert temp : entityList){
                CmdExpert result = this.createOne(temp);
                if(null!=result){
                    resultList.add(result);
                }
            }
            return resultList;
        }else{
            return null;
        }

    }
    //删除应急专家关联信息
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        CmdExpert entity = repository.findOne(id);
        Assert.notNull(entity,"CmdExpert 不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }
    //根据条件查询处置方案已关联的应急专家信息
    public List<CmdExpert> findList(MultiValueMap<String, Object> params){
        return repository.findList(params);
    }

}
