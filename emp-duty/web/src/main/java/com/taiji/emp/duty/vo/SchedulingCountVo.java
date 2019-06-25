package com.taiji.emp.duty.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author pc_qzp
 * @date 2018-11-14 15:44:09
 * 值排班统计
 */
public class SchedulingCountVo {

    /**
     * 值班人员ID
     */
    @Getter
    @Setter
    String personId;

    /**
     * 值班人员名
     */
    @Getter
    @Setter
    String personName;

    /**
     * 统计日志类型及数量
     */
    @Getter
    @Setter
    List<SchedulingVo> data;

    /**
     * 统计总数量
     */
    @Getter
    @Setter
    Integer total;
}
