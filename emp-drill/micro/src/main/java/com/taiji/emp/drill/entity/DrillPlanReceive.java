package com.taiji.emp.drill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 演练计划转发接收实体类 DrillPlanReceive
 * @author qzp-pc
 * @date 2018年11月01日09:36:18
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "DT_DRILL_PLAN_RECEIVE")
public class DrillPlanReceive extends IdEntity<String> {

    public DrillPlanReceive() {}

    /**
     * 演练计划ID
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = DrillPlan.class)
    @JoinColumn(name = "DRILL_PLAN_ID",referencedColumnName = "ID")
    private DrillPlan drillPlan;

    /**
     * 转发类型 0：上报 1:下发
     */
    @Getter
    @Setter
    @Length(max = 1,message = "转发类型 sendType字段最大长度1")
    private String sendType;

    /**
     * 发送人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "发送人 sender字段最大长度50")
    private String sender;

    /**
     * 发送时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime sendTime;

    /**
     * 接收机构ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "接收机构ID orgId字段最大长度36")
    private String orgId;

    /**
     * 接收机构名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "接收机构名称 orgName字段最大长度100")
    private String orgName;

    /**
     * 接收人
     */
    @Getter
    @Setter
    @Length(max = 100,message = "接收人 orgName字段最大长度100")
    private String reciever;

    /**
     * 接收时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime recieveTime;

    /**
     * 接收状态 0:未接收，1:已接收
     */
    @Getter
    @Setter
    @Length(max = 1,message = "转发类型 sendType字段最大长度1")
    private String recieveStatus;


}
