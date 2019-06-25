package com.taiji.emp.res.entity;


import com.taiji.micro.common.entity.IdEntity;
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
 * 预案社会依托资源管理表实体类 PlanSupport
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_PLAN_SUPPORT")
public class PlanSupport extends IdEntity<String> {

    public PlanSupport(){}

    /**
     * 预案ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案ID planId字段最大长度36")
    @Column(name = "PLAN_ID")
    private String planId;

    /**
     * 响应级别ID（事件等级ID）
     */
    @Getter@Setter
    @Length(max = 36,message = "响应级别ID（事件等级ID）eventGradeID字段最大长度36")
    @Column(name = "EVENT_GRADE_ID")
    private String eventGradeId;

    /**
     * 响应级别名称（事件等级名称）
     */
    @Getter@Setter
    @Length(max = 100,message = "响应级别名称（事件等级名称）eventGradeName字段最大长度100")
    @Column(name = "EVENT_GRADE_NAME")
    private String eventGradeName;

    /**
     * 社会依托资源ID
     */
    @Getter
    @Setter
    @Column(name = "SUPPORT_ID")
    @Length(max = 36,message = "社会依托资源ID supportId字段最大长度36")
    private String supportId;
}
