package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.ConditionSetting;
import com.taiji.emp.base.feign.IConditionSettingRestService;
import com.taiji.emp.base.mapper.ConditionSettingMapper;
import com.taiji.emp.base.service.ConditionSettingService;
import com.taiji.emp.base.vo.ConditionSettingVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/29 15:29
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/conditionSet")
public class ConditionSettingController implements IConditionSettingRestService{

    @Autowired
    private ConditionSettingService service;
    @Autowired
    private ConditionSettingMapper mapper;

    /**
     * 新增事件类型对应不同等级的应急响应启动条件（list）
     * @param conditionSettingVos
     * @return
     */
    @Override
    public ResponseEntity<List<ConditionSettingVo>> create(@RequestBody List<ConditionSettingVo> conditionSettingVos) {
        List<ConditionSetting> conditionSettings = mapper.voListToEntityList(conditionSettingVos);
        List<ConditionSetting> resultEntitys = service.create(conditionSettings);
        List<ConditionSettingVo> resultVos = mapper.entityListToVoList(resultEntitys);
        return ResponseEntity.ok(resultVos);
    }

    /**
     * 根据事件类型Id，获取该事件类型不同等级的应急响应条件列表
     * @param eventTypeId
     * @return
     */
    @Override
    public ResponseEntity<List<ConditionSettingVo>> findList(@PathVariable(name="eventTypeId") String eventTypeId) {
        List<ConditionSetting> resultEntitys = service.findListByEventId(eventTypeId);
        List<ConditionSettingVo> resultVos = mapper.entityListToVoList(resultEntitys);
        return ResponseEntity.ok(resultVos);
    }
}
