package com.taiji.emp.zn.controller;

import com.taiji.emp.alarm.vo.AlertVo;
import com.taiji.emp.zn.service.InfoService;
import com.taiji.emp.zn.vo.InfoStatVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 浙能首页 -- 日常信息
 * */
@Slf4j
@RestController
@AllArgsConstructor
public class InfoController {

    InfoService infoService;

    /**
     * 根据条件查询未处理状态的预警信息列表
     * {
         "alertStatus": [
         "string"
         ],
         "topNum": 5
        }
     */
    @PostMapping(path = "/alerts/searchAll")
    ResultEntity findAlerts(@RequestBody Map<String,Object> map){
        if(map.containsKey("alertStatus")){
            List<AlertVo> voList = infoService.findAlerts(map);
            return ResultUtils.success(voList);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 根据条件查询预警/事件信息列表-不分页，并按视图view_alarm_event中的report_time倒序排列
     * {
         "topNum": 10
         }
     */
    @PostMapping(path = "/infos/searchAll")
    ResultEntity findInfos(@RequestBody Map<String,Object> map){
        List<InfoStatVo> voList = infoService.findInfos(map);
        return ResultUtils.success(voList);
    }

}
