package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 预警及事件信息按月查询结果vo对象 --DateStatVo
 */
public class DateStatVo {

    public DateStatVo(){}

    /**
     * 日期（字符串）
     * example: 2018-11
     */
    @Getter@Setter
    private String dateStr;

    /**
     * 该日期下对应数量
     */
    @Getter@Setter
    private Integer num;

}
