package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.CmdSupport;
import com.taiji.emp.event.cmd.repository.CmdSupportRepository;
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
public class CmdSupportService extends BaseService<CmdSupport,String> {

    @Autowired
    private CmdSupportRepository repository;

    //新增单个社会依托资源关联信息
    public CmdSupport createOne(CmdSupport entity){
        Assert.notNull(entity,"CmdSupport 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }
    //新增多个社会依托资源关联信息
    public List<CmdSupport> createList(List<CmdSupport> entityList){
        Assert.notNull(entityList,"entityList 不能为null");
        if(entityList.size()>0){
            List<CmdSupport> resultList = new ArrayList<>();
            for(CmdSupport temp : entityList){
                CmdSupport result = this.createOne(temp);
                if(null!=result){
                    resultList.add(result);
                }
            }
            return resultList;
        }else{
            return null;
        }

    }
    //删除社会依托资源关联信息
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        CmdSupport entity = repository.findOne(id);
        Assert.notNull(entity,"CmdSupport 不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }
    //根据条件查询处置方案已关联的社会依托资源信息
    public List<CmdSupport> findList(MultiValueMap<String, Object> params){
        return repository.findList(params);
    }

}
