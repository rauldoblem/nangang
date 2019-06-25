package com.taiji.emp.alarm.vo;

import lombok.Getter;
import lombok.Setter;

public class AlertReceiveOrgsVo {

    public AlertReceiveOrgsVo(){}

    public AlertReceiveOrgsVo(String receiveOrgs,String content){
        this.receiveOrgs = receiveOrgs;
        this.content = content;
    }

    //接收单位串
    @Getter@Setter
    private String receiveOrgs;

    //通知内容
    @Getter@Setter
    private String content;

}
