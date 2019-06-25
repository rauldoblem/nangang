package com.taiji.emp.alarm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.emp.alarm.vo.NoticeFeedbackVo;
import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 预警通知实体类 AlertNotice
 * @author qizhijie-pc
 * @date 2018年12月12日17:29:24
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "MA_ALERT_RECEIVEORG")
public class AlertNotice extends IdEntity<String>{

    public AlertNotice(){}

    /**
     * 预警信息对象 Id -- 冗余字段(临时变量)
     */
    @Getter
    @Setter
    @Transient
    @Length(max =36,message = "alertId 最大长度不能超过36")
    private String alertId;

    /**
     * 预警信息对象
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Alert.class)
    @JoinColumn(name = "ALERT_ID", referencedColumnName = "id")
    private Alert alert;

    /**
     * 接收部门ID
     */
    @Getter@Setter
    @Length(max =36,message = "receiveOrgId 最大长度不能超过36")
    @NotEmpty(message = "receiveOrgId 不能为空字符串")
    private String receiveOrgId;

    /**
     * 接收部门名称
     */
    @Getter@Setter
    @Length(max =100,message = "receiveOrgName 最大长度不能超过100")
    private String receiveOrgName;

    /**
     * 预警通知要求
     */
    @Getter@Setter
    @Length(max =2000,message = "require 最大长度不能超过2000")
    //@NotEmpty(message = "require 不能为空字符串") 2019-1-4 修改 可以为空
    @Column(name="NOTICE_REQUIRE")
    private String require;

    /**
     * 通知下发时间 -- 创建时间(入库生成)
     */
    @Getter@Setter
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @CreatedDate
    private LocalDateTime noticeTime;

    /**
     * 接收人 -- 暂无
     */
    @Getter@Setter
    @Length(max =50,message = "receiveBy 最大长度不能超过50")
    private String receiveBy;

    /**
     * 接收时间 -- 暂时与下发时间一样，入库生成
     */
    @Getter@Setter
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @CreatedDate
    private LocalDateTime receiveTime;

    /**
     * 接收状态（0：未接收；1：已接收） -- 枚举类
     */
    @Getter@Setter
    @Length(max =1,message = "receiveStatus 最大长度不能超过1")
    private String receiveStatus;

    /**
     * 最新反馈状态（0：未开始；1：进行中；2：已完成）
     */
    @Getter@Setter
    @Length(max =1,message = "feedbackStatus 最大长度不能超过1")
    private String feedbackStatus;

    /**
     * 最新反馈内容
     */
    @Getter@Setter
    @Length(max =2000,message = "feedbackContent 最大长度不能超过2000")
    private String feedbackContent;

    /**
     * 最近反馈时间
     */
    @Getter@Setter
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    private LocalDateTime feedbackLasttime;

    /**
     * 所有反馈(按反馈时间倒叙)
     */
    @Getter@Setter
    @OneToMany(fetch = FetchType.LAZY,targetEntity = NoticeFeedback.class)
    @JoinColumn(name = "ALERT_RECEIVE_ID")
    @OrderBy(value = "feedbackTime desc")
    private List<NoticeFeedback> feedbacks;

}
