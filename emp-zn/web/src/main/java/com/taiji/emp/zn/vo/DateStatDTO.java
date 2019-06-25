package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 按年份各个月份的 事件数量和预警数量
 */
public class DateStatDTO {

    public DateStatDTO(){}

    public DateStatDTO(List<Integer> eventTotalNum,List<Integer> alertTotalNum){
        this.eventTotalNum = eventTotalNum;
        this.alertTotalNum = alertTotalNum;
    }

    @Getter @Setter
    private List<Integer> eventTotalNum;

    @Getter @Setter
    private List<Integer> alertTotalNum;

}
