package com.taiji.emp.event.infoDispatch.entity;

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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 报送信息处理类AcceptDeal
 * @author qizhijie-pc
 * @date 2018年10月23日15:24:59
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "IM_ACCEPT_DEAL")
public class AcceptDeal extends IdEntity<String> {

    public AcceptDeal(){}

    //信息ID(临时变量)
    @Getter@Setter
    @Transient
    @NotEmpty(message = "信息ID不能为空")
    private String acceptId;

    //信息对象
    @Getter@Setter
    @ManyToOne(targetEntity = Accept.class)
    @JoinColumn(name = "ACCEPT_ID",referencedColumnName = "ID")
    private Accept imAccept;

    //创建单位ID
    @Getter@Setter
    @Length(max = 36,message = "创建单位ID长度不能超过36")
    private String createOrgId;

    //创建单位名称
    @Getter@Setter
    @Length(max = 36,message = "创建单位名称长度不能超过36")
    private String createOrgName;

    //接收办理单位ID
    @Getter@Setter
    @Length(max = 36,message = "接收办理单位ID长度不能超过36")
    private String dealOrgId;

    //接收办理单位名称
    @Getter@Setter
    @Length(max = 100,message = "接收办理单位名称长度不能超过100")
    private String dealOrgName;

    //接收后处理状态：0未发送，1已发送，2已退回，3已办结，4已生成事件
    @Getter@Setter
    @Length(max = 1,message = "接收后处理状态长度不能超过1")
    private String dealFlag;

    //办理时间(后台生成)
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
    @LastModifiedDate
    @Getter@Setter
    private LocalDateTime dealTime;

    //退回原因
    @Getter@Setter
    @Length(max = 400,message = "退回原因长度不能超过400")
    private String returnReason;

    //生成事件ID
    @Getter@Setter
    @Length(max = 36,message = "生成事件ID长度不能超过36")
    private String eventId;

    //办理人ID
    @Getter@Setter
    @Length(max = 36,message = "办理人ID长度不能超过36")
    private String dealPersonId;

    //办理人姓名
    @Getter@Setter
    @Length(max = 100,message = "办理人姓名长度不能超过100")
    private String dealPersonName;

}
