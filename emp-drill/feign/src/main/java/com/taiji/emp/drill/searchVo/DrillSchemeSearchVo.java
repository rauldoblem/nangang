package com.taiji.emp.drill.searchVo;

import lombok.Getter;
import lombok.Setter;

public class DrillSchemeSearchVo extends BasePageVo {

    /**
     * 方案名称
     */
    @Getter
    @Setter
    private String drillName;

    @Getter
    @Setter
    private String drillStartTime;

    @Getter
    @Setter
    private String drillEndTime;

    /**
     * 方案概要
     */
    @Getter
    @Setter
    private String summary;

    /**
     * 制定部门ID，为空时默认为登录用户所属单位ID
     */
    @Getter
    @Setter
    private String orgId;
}
