package com.taiji.emp.base.service;

import com.taiji.emp.base.feign.ConditionSettingClient;
import com.taiji.emp.base.vo.ConditionSettingVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/29 15:21
 */
@Service
public class ConditionSettingService {

    @Autowired
    private ConditionSettingClient client;

    /**
     * 新增事件类型对应不同等级的应急响应启动条件（list）
     * @param conditionSettingVos
     */
    public void create(List<ConditionSettingVo> conditionSettingVos) {
        client.create(conditionSettingVos);
    }

    /**
     * 根据事件类型Id，获取该事件类型不同等级的应急响应条件列表
     * @param eventTypeId
     * @return
     */
    public List<ConditionSettingVo> findList(String eventTypeId) {
        ResponseEntity<List<ConditionSettingVo>> list = client.findList(eventTypeId);
        List<ConditionSettingVo> conditionSettingVos = ResponseEntityUtils.achieveResponseEntityBody(list);
        return conditionSettingVos;
    }
}
