package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.CmdPlan;
import com.taiji.emp.event.cmd.repository.CmdPlanRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CmdPlanService extends BaseService<CmdPlan,String> {

    @Autowired
    private CmdPlanRepository repository;

    //新增关联预案
    public List<CmdPlan> createList(List<CmdPlan> list){
        if(null!=list&&list.size()>0){
            List<CmdPlan> resultList = new ArrayList<>();
            for(CmdPlan entity : list){
                entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
                CmdPlan result = repository.save(entity);
                if(null!=result){
                    resultList.add(result);
                }
            }
            return resultList;
        }else{
            return null;
        }

    }

    //根据条件查询处置方案已关联的应急预案信息
    public List<CmdPlan> searchAll(MultiValueMap<String, Object> params){
        return repository.findList(params);
    }

}
