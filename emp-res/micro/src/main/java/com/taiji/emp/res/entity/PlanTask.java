package com.taiji.emp.res.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 预案任务设置管理实体类 PlanTask
 *
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_PLAN_TASK")
public class PlanTask extends BaseEntity<String> implements DelFlag{

    public PlanTask(){}

    /**
     * 预案ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案ID planId字段最大长度36")
    @Column(name = "PLAN_ID")
    private String planID;

    /**
     * 响应级别ID（事件等级ID）
     */
    @Getter@Setter
    @Length(max = 36,message = "响应级别ID eventGradeId字段最大长度36")
    @Column(name = "EVENT_GRADE_ID")
    private String eventGradeId;

    /**
     * 响应级别名称（事件等级名称）
     */
    @Getter@Setter
    @Length(max = 100,message = "响应级别名称eventGradeName字段最大长度100")
    @Column(name = "EVENT_GRADE_NAME")
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
    @Column(name = "PRINCI_NAME")
    private String princiName;

    /**
     * 负责人联系方式
     */
    @Getter@Setter
    @Length(max = 16,message = "负责人联系方式princiTel字段最大长度16")
    @Column(name = "PRINCI_TEL")
    private String princiTel;

    /**
     * 负责部门ID
     */
    @Getter@Setter
    @Length(max = 36,message = "负责部门ID princiOrgId字段最大长度36")
    @Column(name = "PRINCI_ORG_ID")
    private String princiOrgId;

    /**
     * 负责部门名称
     */
    @Getter@Setter
    @Length(max = 100,message = "负责部门名称princiOrgName字段最大长度100")
    @Column(name = "PRINCI_ORG_NAME")
    private String princiOrgName;


    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
