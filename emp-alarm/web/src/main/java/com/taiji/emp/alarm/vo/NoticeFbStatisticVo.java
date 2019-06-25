package com.taiji.emp.alarm.vo;

import lombok.Getter;
import lombok.Setter;

//通知反馈统计结果包装类
public class NoticeFbStatisticVo {

    public NoticeFbStatisticVo(){}

    public NoticeFbStatisticVo(int total,int complete,int progress,int start){
        this.total = total;
        this.complete = complete;
        this.progress = progress;
        this.start = start;
    }

    @Getter@Setter
    private int total;
    @Getter@Setter
    private int complete;
    @Getter@Setter
    private int progress;
    @Getter@Setter
    private int start;

}
