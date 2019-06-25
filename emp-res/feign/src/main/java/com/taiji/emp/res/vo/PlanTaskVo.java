package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 预案任务设置管理
 */
public class PlanTaskVo extends BaseVo<String>{

    public PlanTaskVo(){}

    /**
     * 预案ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案ID planId字段最大长度36")
    private String planID;

    /**
     * 响应级别ID（事件等级ID）
     */
    @Getter@Setter
    @Length(max = 36,message = "响应级别ID eventGradeId字段最大长度36")
    private String eventGradeId;

    /**
     * 响应级别名称（事件等级名称）
     */
    @Getter@Setter
    @Length(max = 100,message = "响应级别名称eventGradeName字段最大长度100")
    private String eventGradeName;

    /**
     * 任务名称
     */
    @Getter@Setter
    @Length(max = 36,message = "任务名称title字段最大长度100")
    private String title;

    /**
     * 任务内容
     */
    @Getter@Setter
    @Length(max = 2000,message = "任务内容content字段最大长度2000")
    private String content;

    /**
     * 负责人
     */
    @Getter@Setter
    @Length(max = 20,message = "负责人princiName字段最大长度20")
    private String princiName;

    /**
     * 负责人联系方式
     */
    @Getter@Setter
    @Length(max = 16,message = "负责人联系方式princiTel字段最大长度16")
    private String princiTel;

    /**
     * 负责部门ID
     */
    @Getter@Setter
    @Length(max = 36,message = "负责部门ID princiOrgId字段最大长度36")
    private String princiOrgId;

    /**
     * 负责部门名称
     */
    @Getter@Setter
    @Length(max = 100,message = "负责部门名称princiOrgName字段最大长度100")
    private String princiOrgName;
}
