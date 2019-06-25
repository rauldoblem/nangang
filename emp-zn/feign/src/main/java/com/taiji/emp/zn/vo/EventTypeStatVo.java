package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 按事件类型统计结果类Vo
 * @author qizhijie-pc
 * @date 2018年12月20日10:57:07
 */
public class EventTypeStatVo {

    public EventTypeStatVo(){}

    @Getter@Setter
    private String eventTypeId;

    @Getter@Setter
    private String eventTypeName;

    @Getter@Setter
    private int totalNum;

}
