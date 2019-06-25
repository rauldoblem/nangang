package com.taiji.emp.alarm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class NoticeFbSaveVo {

    /**
     * 预警通知对象id
     */
    @Getter@Setter
    @Length(max =36,message = "alertReceiveId 最大长度不能超过36")
    @NotEmpty(message = "alertReceiveId 不能为空字符串")
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
    private LocalDateTime feedbackTime;

    /**
     * 反馈状态：1：进行中；2：已完成
     */
    @Getter@Setter
    @NotEmpty(message = "反馈状态 不能为空")
    private String feedbackStatus;


    /**
     * 附件对象id串(待赋值list)
     */
    @Getter @Setter
    private List<String> fileIds;

}
