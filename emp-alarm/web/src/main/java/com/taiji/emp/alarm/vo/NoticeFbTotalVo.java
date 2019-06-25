package com.taiji.emp.alarm.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//用于预警发送方查看页面包装类
public class NoticeFbTotalVo {

    public NoticeFbTotalVo(){}

    @Getter@Setter
    private AlertVo alert;

    @Getter@Setter
    private AlertReceiveOrgsVo alertReceiveOrgs;

    @Getter@Setter
    private NoticeFbStatisticVo statistic;

    @Getter@Setter
    private List<AlertFeedbacks> alertFeedbacks;

}
