package com.taiji.emp.duty.searchVo;

import lombok.Getter;
import lombok.Setter;

public class DutyMan {

    public DutyMan() {}

    /**
     *  所属单位ID
     */
    @Getter
    @Setter
    private String orgId;

    /**
     * 值班人员ID
     */
    @Getter
    @Setter
    private String personId;

    /**
     * 值班人员
     */
    @Getter
    @Setter
    private String dutyName;

    /**
     * 值班人员手机号
     */
    @Getter
    @Setter
    private String dutyTel;
}
