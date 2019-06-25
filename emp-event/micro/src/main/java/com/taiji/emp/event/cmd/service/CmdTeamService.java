package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.CmdTeam;
import com.taiji.emp.event.cmd.repository.CmdTeamRepository;
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
public class CmdTeamService extends BaseService<CmdTeam,String> {

    @Autowired
    private CmdTeamRepository repository;

    //新增单个应急队伍关联信息
    public CmdTeam createOne(CmdTeam entity){
        Assert.notNull(entity,"CmdExpert 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }
    //新增多个应急队伍关联信息
    public List<CmdTeam> createList(List<CmdTeam> entityList){
        Assert.notNull(entityList,"entityList 不能为null");
        if(entityList.size()>0){
            List<CmdTeam> resultList = new ArrayList<>();
            for(CmdTeam temp : entityList){
                CmdTeam result = this.createOne(temp);
                if(null!=result){
                    resultList.add(result);
                }
            }
            return resultList;
        }else{
            return null;
        }

    }
    //删除应急队伍关联信息
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        CmdTeam entity = repository.findOne(id);
        Assert.notNull(entity,"CmdExpert 不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }
    //根据条件查询处置方案已关联的应急队伍信息
    public List<CmdTeam> findList(MultiValueMap<String, Object> params){
        return repository.findList(params);
    }

}
