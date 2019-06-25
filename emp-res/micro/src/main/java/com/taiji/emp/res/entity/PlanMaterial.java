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
 * 预案物资管理表实体类 PlanMaterial
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_PLAN_MATERIAL")
public class PlanMaterial extends IdEntity<String> {

    public PlanMaterial(){}

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
     * 应急物资编码
     */
    @Getter
    @Setter
    @Column(name = "MATERIAL_TYPE_ID")
    @Length(max = 36,message = "应急物资编码materialTypeId字段最大长度36")
    private String materialTypeId;

    /**
     * 应急物资编码名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "应急物资编码名称materialTypeName字段最大长度100")
    @Column(name = "MATERIAL_TYPE_NAME")
    private String itemName;
}
