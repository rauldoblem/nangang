package com.taiji.emp.drill.searchVo;

import com.taiji.emp.drill.vo.DrillPlanVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DrillPlanReceiveSearchVo extends BasePageVo {

    /**
     * 计划名称
     */
    @Getter
    @Setter
    private String drillName;


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
     * 制定部门ID字符串
     */
    @Getter
    @Setter
    private String orgIds;

    /**
     * 接收单位ID，默认登录用户所属单位ID进行接收单位过滤
     */
    @Getter
    @Setter
    private String receiveOrgId;

    /**
     * 演练计划ID
     */
    @Getter@Setter
    private String drillPlanId;

    /**
     * 前端输入参数，转发类型 0：上报 1:下发
     */
    @Getter@Setter
    private String sendType;

    @Getter@Setter
    private List<DrillPlanVo> orgIdANames;
}
