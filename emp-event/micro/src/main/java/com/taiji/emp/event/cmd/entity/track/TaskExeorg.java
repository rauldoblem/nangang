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

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 任务执行单位 管理实体类 TaskExeorg
 * @author SunYi
 * @date 2018年11月8日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_TASK_EXEORG")
public class TaskExeorg extends IdEntity<String>{

    public TaskExeorg(){}


    /**
     * 任务ID
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Task.class)
    @JoinColumn(name = "TASK_ID",referencedColumnName = "ID")
    private Task task;

    /**
     * 责任部门ID
     */
    @Getter
    @Setter
    @NotBlank(message = "责任部门ID 不能为空字符串")
    @Length(max = 36,message = "责任部门ID orgId字段最大长度36")
    private String orgId;

    /**
     * 责任部门名称
     */
    @Getter
    @Setter
    @NotBlank(message = "责任部门名称 不能为空字符串")
    @Length(max = 50,message = "责任部门名称 orgName 字段最大长度50")
    private String orgName;

    /**
     * 负责人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "负责人 principal 字段最大长度50")
    private String principal;
    /**
     * 负责人联系电话
     */
    @Getter
    @Setter
    @Length(max = 50,message = "负责人联系电话  principalTel 字段最大长度50")
    private String principalTel;

    /**
     * 下发时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime sendTime;

}
