package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.CmdMaterial;
import com.taiji.emp.event.cmd.repository.CmdMaterialRepository;
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
public class CmdMaterialService extends BaseService<CmdMaterial,String> {

    @Autowired
    private CmdMaterialRepository repository;

    //新增单个应急物资关联信息
    public CmdMaterial createOne(CmdMaterial entity){
        Assert.notNull(entity,"CmdMaterial 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }
    //新增多个应急物资关联信息
    public List<CmdMaterial> createList(List<CmdMaterial> entityList){
        Assert.notNull(entityList,"entityList 不能为null");
        if(entityList.size()>0){
            List<CmdMaterial> resultList = new ArrayList<>();
            for(CmdMaterial temp : entityList){
                CmdMaterial result = this.createOne(temp);
                if(null!=result){
                    resultList.add(result);
                }
            }
            return resultList;
        }else{
            return null;
        }

    }
    //删除应急物资关联信息
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        CmdMaterial entity = repository.findOne(id);
        Assert.notNull(entity,"CmdMaterial 不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }
    //根据条件查询处置方案已关联的应急物资信息
    public List<CmdMaterial> findList(MultiValueMap<String, Object> params){
        return repository.findList(params);
    }

}
