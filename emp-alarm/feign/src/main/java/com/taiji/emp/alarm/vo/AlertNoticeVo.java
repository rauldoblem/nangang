package com.taiji.emp.alarm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预警通知 Vo类
 * @author qizhijie-pc
 * @date 2018年12月12日15:12:44
 */
public class AlertNoticeVo extends IdVo<String>{

    public AlertNoticeVo(){}

    /**
     * 预警信息对象
     */
    @Getter@Setter
    private AlertVo alert;

    /**
     * 预警信息对象 Id -- 冗余字段
     */
    @Getter@Setter
    @Length(max =36,message = "alertId 最大长度不能超过36")
    private String alertId;

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
    @NotEmpty(message = "require 不能为空字符串")
    private String require;

    /**
     * 通知下发时间 -- 创建时间
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
    private LocalDateTime noticeTime;

    /**
     * 接收人 -- 暂无
     */
    @Getter@Setter
    @Length(max =50,message = "receiveBy 最大长度不能超过50")
    private String receiveBy;

    /**
     * 接收时间 -- 暂时与下发时间一样
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
    private LocalDateTime receiveTime;

    /**
     * 接收状态（0：未接收；1：已接收） -- 常量
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
     * 所有反馈
     */
    @Getter@Setter
    private List<NoticeFeedbackVo> feedbacks;

}
