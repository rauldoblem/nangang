package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 预警及事件信息按月统计结果vo包装对象 --DateStatVo
 */
public class EventAndAlertDateStatVo {

    public EventAndAlertDateStatVo(){}

    public EventAndAlertDateStatVo(Map<String,Integer> eventStatMap,Map<String,Integer> alertStatMap){
        this.eventStatMap = eventStatMap;
        this.alertStatMap = alertStatMap;
    }

    /**
     * 事件按月统计对象
     * example: {'2018-11':5,'2018-12':6}
     */
    @Getter@Setter
    private Map<String,Integer> eventStatMap;

    /**
     * 预警信息按月统计对象
     * example: {'2018-11':5,'2018-12':6}
     */
    @Getter@Setter
    private Map<String,Integer> alertStatMap;

}
