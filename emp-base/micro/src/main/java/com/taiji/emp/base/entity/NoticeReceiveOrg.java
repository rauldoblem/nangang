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
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 公告反馈关系实体类 NoticeReceiveOrg
 * @author qzp-pc
 * @date 2018年10月18日17:57:57
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_NOTICE_RECIEVE_ORG")
public class NoticeReceiveOrg extends IdEntity<String> {

    public NoticeReceiveOrg() {}

    /**
     * 通知信息ID
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = Notice.class)
    @JoinColumn(name = "NOTICE_ID",referencedColumnName = "ID")
    private Notice notice;

    /**
     * 发送人
     */
    @Getter
    @Setter
    @NotBlank(message = "标题不能为空字符串")
    @Length(max = 50,message = "发送人 sendBy最大长度50")
    private String sendBy;

    /**
     * 发送时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime sendTime;

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
    @NotBlank(message = "标题不能为空字符串")
    @Length(max = 50,message = "接收部门名称 receiveOrgName最大长度50")
    private String receiveOrgName;

    /**
     * 是否反馈：0未反馈，1已反馈
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否反馈 isFeedback最大长度1")
    private String isFeedback;
}
