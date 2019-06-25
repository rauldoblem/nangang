package com.taiji.emp.drill.searchVo;

import lombok.Getter;
import lombok.Setter;

public class DrillPlanSearchVo extends BasePageVo {

    /**
     * 计划名称
     */
    @Getter
    @Setter
    private String drillName;

    /**
     * 演练单位ID
     */
    @Getter
    @Setter
    private String partOrgIds;

    /**
     * 演练开始时间
     */
    @Getter@Setter
    private String drillStartTime;

    /**
     * 演练结束时间
     */
    @Getter@Setter
    private String drillEndTime;

    /**
     * 演练方式ID
     */
    @Getter
    @Setter
    private String drillWayId;

    /**
     * 制定部门ID，为空时默认为登录用户所属单位ID
     */
    @Getter
    @Setter
    private String orgId;
}
