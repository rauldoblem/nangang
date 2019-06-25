package com.taiji.emp.base.entity;

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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 反馈实体类 NoticeFeedBack
 * @author qzp-pc
 * @date 2018年10月18日18:13:13
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ed_notice_feedback")
public class NoticeFeedBack  extends IdEntity<String> {

    public NoticeFeedBack() {}

    /**
     * 通知接收ID
     */
    @Getter
    @Setter
    private String noticeReceiveId;

    /**
     * 反馈人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "反馈人 feedBackBy字段最大长度50")
    private String feedbackBy;

    /**
     * 反馈内容
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "反馈内容 content字段最大长度2000")
    private String content;

    /**
     * 反馈时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    @Column(name = "FEEDBACK_TIME")
    private LocalDateTime feedBackTime;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "备注 notes字段最大长度2000")
    private String notes;
}
