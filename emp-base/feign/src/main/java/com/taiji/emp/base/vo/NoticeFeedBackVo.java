package com.taiji.emp.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 通知公告反馈 实体类 feign NoticeFeedBackVo
 * @author qzp-pc
 * @date 2018年10月23日15:09:03
 */
public class NoticeFeedBackVo extends BaseVo<String> {

    public NoticeFeedBackVo() {}

    /**
     * 通知接收ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "通知接收ID noticeRecId字段最大长度36")
    private String noticeRecId;


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
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime feedbackTime;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "备注 notes字段最大长度2000")
    private String notes;

}
