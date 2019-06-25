package com.taiji.emp.event.cmd.vo.trackVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * 任务执行单位 管理实体类vo TaskExeorgVo
 * @author SunYi
 */
public class TaskExeorgVo extends IdVo<String> {

    public TaskExeorgVo(){}


    /**
     * 任务
     */
    @Getter
    @Setter
    private TaskVo task;

    /**
     * 任务ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "任务ID  taskId 字段最大长度36")
    private String taskId;


    /**
     * 责任部门ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "责任部门ID orgId字段最大长度36")
    private String orgId;

    /**
     * 责任部门名称
     */
    @Getter
    @Setter
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
     * 发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //@CreatedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime sendTime;

}
