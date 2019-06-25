package com.taiji.emp.event.cmd.searchVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 处置跟踪 时间轴 EcTaskVo
 * @author SunYi
 */
public class TaskPageVo extends IdVo<String> {


    @Getter @Setter
    private int page;

    @Getter @Setter
    private int size;

    @Getter @Setter
    private String schemeId;
    @Getter @Setter
    private String eventName;
    @Getter @Setter
    private String schemeName;
    @Getter @Setter
    private String taskName;
    @Getter @Setter
    private List<String> taskStatus;

    /**
     * 任务下发时间，格式为yyyy-MM-dd HH:mm:SS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private List<LocalDateTime> createTimes;

    /**
     * 任务分类标志位 0下发任务列表 （通过TC_TASK表里的任务创建单位create_org_id与当前登录用户所属部门匹配查询）
     *               1接收任务列表 （通过EC_TASK_EXEORG里的任务接收单位org_id与当前登录用户所属部门匹配查询）
     */
    @Getter @Setter
    private String flag;

    /**
     * 当前用户orgId
     */
    @Getter @Setter
    private String orgId;

    /**
     * 创建时间段-开始时间，格式为yyyy-MM-dd HH:mm:SS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime createStartTime;

    /**
     * 创建时间段-截止时间，格式为yyyy-MM-dd HH:mm:SS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime createEndTime;

}
