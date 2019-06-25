package com.taiji.emp.alarm.entity;

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
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 预警通知反馈实体类 NoticeFeedback
 * @author qizhijie-pc
 * @date 2018年12月12日17:29:24
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "MA_ALERT_FEEDBACK")
public class NoticeFeedback extends IdEntity<String> {

    public NoticeFeedback(){}

    /**
     * 消息通知对象id
     */
    @Getter
    @Setter
    @Length(max =36,message = "alertReceiveId 最大长度不能超过36")
    @NotEmpty(message = "alertReceiveId 不能为空字符串")
    @Column(name="ALERT_RECEIVE_ID")
    private String alertReceiveId;

    /**
     * 反馈人
     */
    @Getter@Setter
    @Length(max =50,message = "feedbackBy 最大长度不能超过50")
    @NotEmpty(message = "feedbackBy 不能为空字符串")
    private String feedbackBy;

    /**
     * 反馈内容
     */
    @Getter@Setter
    @Length(max =2000,message = "content 最大长度不能超过2000")
    private String content;

    /**
     * 问题
     */
    @Getter@Setter
    @Length(max =2000,message = "question 最大长度不能超过2000")
    private String question;

    /**
     * 反馈时间 (后台生成)
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
    private LocalDateTime feedbackTime;

}
