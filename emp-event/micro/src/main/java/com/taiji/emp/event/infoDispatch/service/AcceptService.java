package com.taiji.emp.event.infoDispatch.service;

import com.taiji.emp.event.common.enums.RepStatusEnum;
import com.taiji.emp.event.infoDispatch.entity.Accept;
import com.taiji.emp.event.infoDispatch.entity.AcceptDeal;
import com.taiji.emp.event.infoDispatch.repository.AcceptDealRepository;
import com.taiji.emp.event.infoDispatch.repository.AcceptRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@AllArgsConstructor
public class AcceptService extends BaseService<Accept,String>{

    @Autowired
    private AcceptRepository repository;
    @Autowired
    private AcceptDealRepository dealRepository;

    //初报/续报 -- 需要生成AcceptDeal信息
    public Accept createInfo(Accept accept){
        accept.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Accept result = repository.save(accept);

        if((RepStatusEnum.FIRST.getCode()).equals(result.getIsFirst())){
            result.setFirstReportId(result.getId()); //如果是初报，给firstReportId字段赋值自身
            result = repository.save(result);
        }
        accept.setId(result.getId());
        AcceptDeal deal = dealRepository.createInfo(accept);

        result.setDealId(deal.getId());
        return result;
    }

    //编辑保存
    public Accept editSave(Accept accept,String id){
        Assert.notNull(accept,"accept 不能为 null");
        Assert.hasText(id,"id 不能为空字符串");
        accept.setId(id);
        String dealId = accept.getDealId();
        Accept result = repository.save(accept);
        result.setDealId(dealId);
        return result;
    }

    //根据Id获取单条 初报/续报信息
    public Accept findOne(String id){
        Assert.hasText(id,"id 不能为空字符串");
        return repository.findOne(id);
    }

    //逻辑删除单条报送信息
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        Accept entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }



}
