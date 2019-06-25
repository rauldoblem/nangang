package com.taiji.emp.drill.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 演练方案 实体类 feign DrillSchemeVo
 * @author qzp-pc
 * @date 2018年11月05日10:20:18
 */
public class DrillSchemeVo extends BaseVo<String> {

    public DrillSchemeVo() {}

    /**
     * 演练计划ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "演练计划ID drillPlanId字段最大长度36")
    private String drillPlanId;

    /**
     * 演练计划名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "演练计划名称 drillPlanName字段最大长度100")
    private String drillPlanName;

    /**
     * 演练名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "演练名称 drillName字段最大长度100")
    private String drillName;

    /**
     * 演练内容
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "演练名称 summary字段最大长度4000")
    private String summary;

    /**
     * 相关预案ID串
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "相关预案ID串 epPlanIds字段最大长度4000")
    private String epPlanIds;

    /**
     * 相关预案名称串
     */
    @Getter
    @Setter
    @Length(max = 1000,message = "相关预案名称串 epPlanNames字段最大长度1000")
    private String epPlanNames;

    /**
     * 下发状态(0.未下发1.已下发）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "下发状态 sendStatus字段最大长度1")
    private String sendStatus;

    /**
     * 上报状态(0.未上报1.已上报）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "上报状态 reportStatus字段最大长度1")
    private String reportStatus;

    /**
     * 制定单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "制定单位ID orgId字段最大长度36")
    private String orgId;

    /**
     * 制定单位名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "制定单位名称 orgName字段最大长度100")
    private String orgName;
}
