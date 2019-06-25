package com.taiji.emp.res.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 预案管理基础实体类 PlanBase
 *
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_PLAN_BAS")
public class PlanBase extends BaseEntity<String> implements DelFlag{

    public PlanBase(){}

    /**
     * 预案名称
     */
    @Getter@Setter
    @Length(max = 100,message = "预案名称name字段最大长度100")
    private String name;

    /**
     * 发布单位
     */
    @Getter@Setter
    @Length(max = 100,message = "发布单位code字段最大长度100")
    private String unit;

    /**
     * 预案类型ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案类型ID planTypeId字段最大长度36")
    @Column(name = "PLAN_TYPE_ID")
    private String planTypeId;

    /**
     * 预案类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "预案类型名称planTypeName字段最大长度100")
    @Column(name = "PLAN_TYPE_NAME")
    private String planTypeName;

    /**
     * 事件类型ID
     */
    @Getter@Setter
    @Length(max = 36,message = "事件类型ID eventTypeId字段最大长度36")
    @Column(name = "EVENT_TYPE_ID")
    private String eventTypeId;

    /**
     * 事件类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "事件类型名称eventTypeName字段最大长度100")
    @Column(name = "EVENT_TYPE_NAME")
    private String eventTypeName;

    /**
     * 预案状态ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案状态ID planStatusId字段最大长度36")
    @Column(name = "PLAN_STATUS_ID")
    private String planStatusId;

    /**
     * 预案状态名称
     */
    @Getter@Setter
    @Length(max = 100,message = "预案状态名称planStatusName字段最大长度100")
    @Column(name = "PLAN_STATUS_NAME")
    private String planStatusName;

    /**
     * 编制时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter@Setter
    @Column(name = "COMPILE_TIME")
    private LocalDate compileTime;

    /**
     * 预案描述
     */
    @Getter@Setter
    @Length(max = 2000,message = "预案描述planDescri字段最大长度2000")
    @Column(name = "PLAN_DESCRI")
    private String planDescri;

    /**
     * 备注
     */
    @Getter@Setter
    @Length(max = 2000,message = "备注notes字段最大长度2000")
    private String notes;

    /**
     * 创建单位ID
     */
    @Getter@Setter
    private String createOrgId;

    /**
     * 创建单位编码
     */
    @Getter@Setter
    private String createOrgName;

    /**
     * 预案树的ID(PlanCalTree)
     */
    @Getter@Setter
    @Column(name="PLAN_CALTREE_ID")
    private String planCalTreeId;


    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
