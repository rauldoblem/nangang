package com.taiji.emp.event.cmd.searchVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
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
public class EcTaskVo extends IdVo<String> {

    public EcTaskVo(){}

    /**
     * 任务名称
     */
    @Getter
    @Setter
    private String name;

    /**
     * 责任部门名称
     */
    @Getter
    @Setter
    private String taskExeorg;


    /**
     * 任务状态 0未下发，1已下发，2已完成
     */
    @Getter
    @Setter
    private String taskStatus;


    /**
     * 任务下发时间，格式为yyyy-MM-dd HH:mm:SS
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime taskSendTime;



    /**
     * 反馈信息集合 TaskFeedbackVo
     */
    @Getter
    @Setter
    private List<TaskFeedbackVo> taskFeedback;


}
