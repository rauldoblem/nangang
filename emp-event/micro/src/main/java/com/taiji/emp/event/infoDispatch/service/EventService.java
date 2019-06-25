package com.taiji.emp.event.infoDispatch.service;

import com.taiji.emp.event.common.constant.EventGlobal;
import com.taiji.emp.event.infoDispatch.entity.Event;
import com.taiji.emp.event.infoDispatch.repository.EventRepository;
import com.taiji.emp.event.infoDispatch.searchVo.EventPageVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventService extends BaseService<Event,String>{

    @Autowired
    private EventRepository repository;

    //生成事件
    public Event create(Event event){
        event.setHandleFlag(EventGlobal.EVENT_HANDLE_DOING);//未处置
        return repository.save(event);
    }

    //获取单条事件信息
    public Event findEventById(String id){
        Assert.hasText(id,"id 不能为空字符串");
        Event result = repository.findOne(id);
        return result;
    }

    //更新事件
    public Event update(Event event,String id){
        Assert.hasText(id,"id 不能为空字符串");
        //event.setHandleFlag(EventGlobal.EVENT_HANDLE_DOING);//未处置
        return repository.save(event);
    }

    //根据条件查询事件列表-分页
    public Page<Event> findPage(EventPageVo pageVo, Pageable pageable){
        return repository.findPage(pageVo,pageable);
    }

    //根据条件查询事件列表-不分页
    public List<Event> findList(EventPageVo pageVo){
        return repository.findList(pageVo);
    }

    //事件处置结束/评估/归档，将事件处置状态置为已结束/已评估/已归档状态
    public Event operateEvent(Event event){
//        event.setHandleFlag(EventGlobal.EVENT_HANDLE_FINISH);//处置结束
        return repository.save(event);
    }

}
