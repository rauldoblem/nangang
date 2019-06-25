package com.taiji.emp.event.cmd.entity.track;

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

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * 任务反馈 管理实体类 TaskFeedback
 * @author SunYi
 * @date 2018年11月7日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_TASK_FEEDBACK")
public class TaskFeedback extends IdEntity<String> {

    public TaskFeedback(){}


    /**
     * 任务执行单位ID
     */
    @Getter
    @Setter
    @NotBlank(message = "任务执行单位ID不能为空字符串")
    @Length(max = 36,message = "任务执行单位ID taskOrgId 字段最大长度36")
    private String taskOrgId;

    /**
     * 反馈内容
     */
    @Getter
    @Setter
    @NotBlank(message = "反馈内容不能为空字符串")
    @Length(max = 2000,message = "反馈内容 content 字段最大长度2000")
    private String content;

    /**
     * 反馈人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "反馈人 feedbackBy 字段最大长度50")
    private String feedbackBy;

    /**
     * 是否完成：0否 1是
     * COMPLETE_STATUS
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否完成 completeStatus 字段最大长度1")
    private String completeStatus;


    /**
     * 反馈日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime feedbackTime;

    /**
     * 任务ID
     */
    @Getter
    @Setter
    @NotBlank(message = "任务ID不能为空字符串")
    @Length(max = 36,message = "任务执 taskOrgId 字段最大长度36")
    private String taskId;

    /**
     * 反馈类型：0反馈 1督办
     */
    @Getter
    @Setter
    @Length(max = 1,message = "反馈类型 feedbackType 字段最大长度1")
    private String feedbackType;
}
