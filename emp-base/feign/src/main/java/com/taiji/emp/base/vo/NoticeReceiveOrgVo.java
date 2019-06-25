package com.taiji.emp.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 通知公告反馈关系 实体类 feign NoticeReceiveOrgVo
 * @author qzp-pc
 * @date 2018年10月22日17:29:03
 */
public class NoticeReceiveOrgVo extends IdVo<String> {

    public NoticeReceiveOrgVo() {}

    /**
     * 通知信息ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "通知信息ID noticeId最大长度36")
    private String noticeId;

    /**
     * 通知名称
     */
    @Getter
    @Setter
    @Length(max = 200,message = "通知名称title最大长度200")
    private String title;

    /**
     * 通知类型ID
     */
    @Getter
    @Setter
    private String noticeTypeId;

    /**
     * 通知类型名称
     */
    @Getter
    @Setter
    private String noticeTypeName;

    /**
     * 内容
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "内容content最大长度2000")
    private String content;

    /**
     * 创建单位ID
     */
    @Getter
    @Setter
    private String buildOrgId;

    /**
     * 创建单位名称
     */
    @Getter
    @Setter
    private String buildOrgName;

    /**
     * 发送人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "发送人 sendBy最大长度50")
    private String sendBy;

    /**
     * 接收部门ID
     */
    @Getter
    @Setter
    @NotBlank(message = "接收部门ID不能为空字符串")
    @Length(max = 36,message = "接收部门ID receiveOrgId最大长度36")
    private String receiveOrgId;

    /**
     * 接收部门名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "接收部门名称 receiveOrgName最大长度50")
    private String receiveOrgName;

    /**
     * 发送时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime sendTime;

    /**
     * 是否反馈：0未反馈，1已反馈
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否反馈 isFeedback最大长度1")
    private String isFeedback;

    /**
     * 反馈内容
     */
    @Getter
    @Setter
    private String feedbackContent;

    /**
     * 反馈人
     */
    @Getter
    @Setter
    private String feedbackBy;

    /**
     * 反馈时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime feedbackTime;
}
