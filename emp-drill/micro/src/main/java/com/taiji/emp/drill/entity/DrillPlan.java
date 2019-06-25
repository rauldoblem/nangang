package com.taiji.emp.drill.entity;

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

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 演练计划实体类 DrillPlan
 * @author qzp-pc
 * @date 2018年11月01日09:36:18
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "DT_DRILL_PLAN")
public class DrillPlan extends BaseEntity<String> implements DelFlag {

    public DrillPlan() {}

    /**
     * 演练名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "演练名称drillName字段最大长度100")
    private String drillName;

    /**
     * 演练方式ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "演练方式ID drillWayId字段最大长度36")
    private String drillWayId;

    /**
     * 演练方式名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "演练方式名称 drillWayId字段最大长度50")
    private String drillWayName;

    /**
     * 演练时间(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter@Setter
    private LocalDate drillTime;

    /**
     * 演练地点
     */
    @Getter
    @Setter
    @Length(max = 100,message = "演练地点drillPlace字段最大长度100")
    private String drillPlace;

    /**
     * 参与单位ID串
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "参与单位ID串 partOrgIds字段最大长度4000")
    private String partOrgIds;

    /**
     * 参与单位名称串
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "参与单位名称串 partOrgNames字段最大长度4000")
    private String partOrgNames;

    /**
     * 演练内容
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "演练内容 content字段最大长度4000")
    private String content;

    /**
     * 相关预案ID串
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "相关预案ID串 epPlanIds字段最大长度2000")
    private String epPlanIds;

    /**
     * 相关预案名称串
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "相关预案名称串 epPlanNames字段最大长度4000")
    private String epPlanNames;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "备注 notes字段最大长度2000")
    private String notes;

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

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
