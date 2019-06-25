package com.taiji.emp.event.infoConfig.service;

import com.taiji.emp.event.cmd.entity.track.TaskFeedback;
import com.taiji.emp.event.infoConfig.entity.AcceptRule;
import com.taiji.emp.event.infoConfig.repository.AccRuleRepository;
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
public class AccRuleService extends BaseService<AcceptRule,String> {

    @Autowired
    AccRuleRepository ruleRepository;

    //创建接报要求
    public AcceptRule create(AcceptRule entity){
        Assert.notNull(entity,"AcceptRule 对象不能为 null");
        return ruleRepository.save(entity);
    }

    //更新接报要求
    public AcceptRule update(AcceptRule entity,String id){
        Assert.notNull(entity,"AcceptRule 对象不能为 null");
        Assert.hasText(id,"id 不能为null或空字符串");
        return ruleRepository.save(entity);
    }

    //根据查询条件查询接报要求
    //key:eventTypeId
    public AcceptRule getRuleSetting(MultiValueMap<String,Object> params){
        return ruleRepository.getRuleSetting(params);
    }

    //获取一键事故的描述信息
    public List<AcceptRule> eventDesc(String eventTypeId) {
        List<AcceptRule> result = ruleRepository.eventDesc(eventTypeId);
        return result;
    }
}
