package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.Scheme;
import com.taiji.emp.event.cmd.repository.SchemeRepository;
import com.taiji.emp.event.infoDispatch.entity.Event;
import com.taiji.emp.event.infoDispatch.repository.EventRepository;
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
public class SchemeService extends BaseService<Scheme,String> {

    @Autowired
    private SchemeRepository repository;
    @Autowired
    private EventRepository eventRepository;

    //应急处置--处置方案信息新增
    public Scheme create(Scheme entity){
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    //根据事件id 获取处置方案Scheme
    public Scheme findByEventId(String eventId){
        Assert.hasText(eventId,"eventId 不能为空字符串");
        return repository.findByEventId(eventId);
    }

    //更新处置方案
    public Scheme update(Scheme entity,String id){
        Assert.hasText(id,"id 不能为空字符串");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    //根据方案id获取事件对象
    public Event findEventBySchemeId(String schemeId){
        Scheme scheme = repository.findOne(schemeId);
        String eventId = scheme.getEventId();
        Assert.hasText(eventId,"eventId 不能为空字符串");
        Event result = eventRepository.findOne(eventId);
        return result;
    }

    public Scheme findOne(String id) {
        Assert.hasText(id,"id 不能为空字符串");
        Scheme scheme = repository.findOne(id);
        return scheme;
    }
}
