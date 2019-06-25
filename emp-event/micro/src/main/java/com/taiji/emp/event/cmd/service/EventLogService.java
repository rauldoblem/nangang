package com.taiji.emp.event.cmd.service;

import com.taiji.emp.event.cmd.entity.EventLog;
import com.taiji.emp.event.cmd.repository.EventLogRepository;
import com.taiji.emp.event.cmd.vo.EventLogVo;
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
public class EventLogService extends BaseService<EventLog,String> {

    @Autowired
    private EventLogRepository repository;

    /**
     * 新增事件应急日志
     * @param entity
     * @return
     */
    public EventLog addEventLog(EventLog entity) {
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    /**
     * 删除事件应急日志
     * @param id
     * @param delFlagEnum
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为空");
        EventLog entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    /**
     * 修改事件应急日志
     * @param entity
     * @return
     */
    public EventLog update(EventLog entity) {
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    /**
     * 获取单条事件应急日志信息
     * @param id
     * @return
     */
    public EventLog findOne(String id) {
        Assert.hasText(id,"id不能为空");
        return repository.findOne(id);
    }

    /**
     * 查询事件应急日志列表
     * @return
     */
    public List<EventLog> findList(EventLogVo vo) {
        return repository.findList(vo);
    }
}
