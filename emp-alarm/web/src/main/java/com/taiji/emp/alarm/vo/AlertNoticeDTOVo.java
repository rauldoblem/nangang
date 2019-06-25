package com.taiji.emp.alarm.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//追加通知 获取已通知信息包装类
public class AlertNoticeDTOVo {

    public AlertNoticeDTOVo(){}

    public AlertNoticeDTOVo(AlertVo alert, String content, String receiveOrgNameStr, List<String> receiveOrgIds){
        this.alert = alert;
        this.content = content;
        this.receiveOrgNameStr = receiveOrgNameStr;
        this.receiveOrgIds = receiveOrgIds;
    }

    //预警信息
    @Getter@Setter
    private AlertVo alert;

    //通知内容
    @Getter@Setter
    private String content;

    //已通知单位中文名（拼接结果）
    @Getter@Setter
    private String receiveOrgNameStr;

    //已通知单位id串
    @Getter@Setter
    private List<String> receiveOrgIds;

}
