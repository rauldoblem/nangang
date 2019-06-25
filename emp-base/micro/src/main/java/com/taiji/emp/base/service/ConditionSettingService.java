package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.ConditionSetting;
import com.taiji.emp.base.repository.ConditionSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/29 15:32
 */
@Service
public class ConditionSettingService {

    @Autowired
    private ConditionSettingRepository repository;

    /**
     * 新增事件类型对应不同等级的应急响应启动条件（list）
     * 每次添加之前先把表格清空
     * @param conditionSettings
     * @return
     */
    @Transactional
    public List<ConditionSetting> create(List<ConditionSetting> conditionSettings) {
        repository.deleteAll();
        List<ConditionSetting> resultList = repository.save(conditionSettings);
        return resultList;
    }

    /**
     * 根据事件类型Id，获取该事件类型不同等级的应急响应条件列表
     * @param eventTypeId
     * @return
     */
    public List<ConditionSetting> findListByEventId(String eventTypeId) {
        List<ConditionSetting> resultList = repository.findListByEventId(eventTypeId);
        return resultList;
    }
}
