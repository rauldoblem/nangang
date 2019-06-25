package com.taiji.emp.event.cmd.entity.track;

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
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 处置跟踪管理实体类 Task
 * @author SunYi
 * @date 2018年11月7日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_TASK")
public class Task extends BaseEntity<String> implements DelFlag {

    public Task(){}

    /**
     * 处置方案ID
     */
    @Getter
    @Setter
    @NotBlank(message = "处置方案ID不能为空字符串")
    @Length(max = 36,message = "处置方案ID schemeId 字段最大长度36")
    private String schemeId;

    /**
     * 处置方案名称
     */
    @Getter
    @Setter
    @NotBlank(message = "处置方案名称不能为空字符串")
    @Length(max = 200,message = "处置方案名称 schemeName 字段最大长度200")
    private String schemeName;

    /**
     * 事件ID
     */
    @Getter
    @Setter
    @NotBlank(message = "事件ID 不能为空字符串")
    @Length(max = 36,message = "事件ID  eventId 字段最大长度36")
    private String eventId;

    /**
     * 事件名称
     */
    @Getter
    @Setter
    @NotBlank(message = "事件名称 不能为空字符串")
    @Length(max = 100,message = "事件名称 eventName 字段最大长度100")
    private String eventName;

    /**
     * 预案ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "预案ID  planId 字段最大长度36")
    private String planId;

    /**
     * 预案关联任务ID
     * PLAN_TASK_ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "预案关联任务ID  planTaskId 字段最大长度36")
    private String planTaskId;
    /**
     * 创建人单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "预案ID  createOrgId 字段最大长度36")
    private String createOrgId;
    /**
     * 创建人单位名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "创建人单位名称  createOrgName 字段最大长度100")
    private String createOrgName;


    /**
     * 任务标题
     */
    @Getter
    @Setter
    @NotBlank(message = "任务标题 不能为空字符串")
    @Length(max = 200,message = "任务标题 name 字段最大长度200")
    private String name;

    /**
     * 任务内容
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "任务内容  content 字段最大长度2000")
    private String content;


    /**
     * 任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime startTime;
    /**
     * 任务结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime endTime;

    /**
     *  任务状态(0.未下发1.已下发2.已完成）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "任务状态  taskStatus 字段最大长度1")
    private String taskStatus;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
