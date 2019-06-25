package com.taiji.emp.event.eva.service;

import com.taiji.emp.event.eva.entity.EventEvaItem;
import com.taiji.emp.event.eva.repository.EventEvaItemRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventEvaItemService extends BaseService<EventEvaItem,String> {

    @Autowired
    EventEvaItemRepository repository;

    /**
     * 新增事件评估项
     * @param entity
     * @return
     */
    public EventEvaItem create(EventEvaItem entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        EventEvaItem eventEvaItem = repository.save(entity);
        return eventEvaItem;
    }

    /**
     * 删除事件评估项信息
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串!");
        EventEvaItem entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 修改事件评估项信息
     * @param entity
     * @return
     */
    public EventEvaItem update(EventEvaItem entity) {
        Assert.notNull(entity,"entity对象不能为空");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        EventEvaItem eventEvaItem = repository.save(entity);
        return eventEvaItem;
    }

    /**
     * 获取单条事件评估项信息
     * @param id
     * @return
     */
    public EventEvaItem findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串");
        EventEvaItem eventEvaItem = repository.findOne(id);
        return eventEvaItem;
    }

    /**
     * 根据事件类型获取事件评估项列表
     * @param eventTypeId
     * @return
     */
    public List<EventEvaItem> findListByEventTypeId(String eventTypeId) {
        List<EventEvaItem> list = repository.findListByEventTypeId(eventTypeId);
        return list;
    }
}
