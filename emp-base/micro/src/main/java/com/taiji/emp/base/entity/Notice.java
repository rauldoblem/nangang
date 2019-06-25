package com.taiji.emp.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 通知公告实体类 Notice
 * @author qzp-pc
 * @date 2018年10月18日17:36:18
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ed_notice")
public class Notice extends BaseEntity<String> implements DelFlag {

    public Notice() {}

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
     * 下发状态：0未下发，1已下发
     */
    @Getter
    @Setter
    @Length(max = 1,message = "下发状态sendStatus最大长度1")
    private String sendStatus;

    /**
     * 下发时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime sendTime;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "备注content最大长度2000")
    private String notes;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
