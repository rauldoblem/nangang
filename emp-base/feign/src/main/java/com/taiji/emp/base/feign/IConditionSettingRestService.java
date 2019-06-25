package com.taiji.emp.base.feign;

import com.taiji.emp.base.vo.ConditionSettingVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/29 15:25
 */
@FeignClient(value = "micro-base-conditionSet")
public interface IConditionSettingRestService {
    /**
     * 新增事件类型对应不同等级的应急响应启动条件（list）
     * @param conditionSettingVos
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/create")
    @ResponseBody
    ResponseEntity<List<ConditionSettingVo>> create(@RequestBody List<ConditionSettingVo> conditionSettingVos);

    /**
     * 根据事件类型Id，获取该事件类型不同等级的应急响应条件列表
     * @param eventTypeId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/create/{eventTypeId}")
    @ResponseBody
    ResponseEntity<List<ConditionSettingVo>> findList(@PathVariable(name = "eventTypeId") String eventTypeId);
}
