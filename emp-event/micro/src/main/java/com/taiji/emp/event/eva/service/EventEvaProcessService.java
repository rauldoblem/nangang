package com.taiji.emp.event.eva.service;

import com.taiji.emp.event.eva.entity.EventEvaProcess;
import com.taiji.emp.event.eva.repository.EventEvaProcessRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class EventEvaProcessService extends BaseService<EventEvaProcess,String> {

    private EventEvaProcessRepository repository;

    public EventEvaProcess create(EventEvaProcess entity){
        Assert.notNull(entity,"Contact 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        EventEvaProcess result = repository.save(entity);
        return result;
    }

    public EventEvaProcess update(EventEvaProcess entity, String id){
        Assert.notNull(entity,"Contact 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        entity.setId(id);
        EventEvaProcess result = repository.save(entity);
        return result;
    }

    public EventEvaProcess findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        EventEvaProcess result = repository.findOne(id);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        EventEvaProcess entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }


    public List<EventEvaProcess> findProcess(String eventId){
        List<EventEvaProcess> result = repository.findList(eventId);
        return result;
    }


}
