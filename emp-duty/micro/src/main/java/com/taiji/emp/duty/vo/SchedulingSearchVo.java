package com.taiji.emp.duty.vo;

import lombok.Getter;
import lombok.Setter;

public class SchedulingSearchVo {

    public SchedulingSearchVo() {}

    /**
     *  所属单位ID
     */
    @Getter
    @Setter
    private String orgId;

    /**
     * 当月的第一天
     */
    @Setter
    @Getter
    private String firstDay;

    /**
     * 当月的最后一天
     */
    @Setter
    @Getter
    private String lastDay;

    /**
     *  值班分组的值班类型编码（0：按班次值班，1：按天值班）
     */
    @Getter
    @Setter
    private String pTypeCode;

    /**
     *  上个年月 eg:2018-12
     */
    @Setter
    @Getter
    private String previousDutyDate;
}
