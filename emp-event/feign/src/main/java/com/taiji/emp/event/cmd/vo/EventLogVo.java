package com.taiji.emp.event.cmd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class EventLogVo extends BaseVo<String> {

    public EventLogVo() {}

    /**
     * 事件信息ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "事件信息ID eventId字段最大长度36")
    private String eventId;

    /**
     * 事件名称
     */
    @Getter
    @Setter
    @Length(max = 200,message = "事件名称 eventName字段最大长度200")
    private String eventName;

    /**
     * 应急日志内容
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "应急日志内容 logContent字段最大长度4000")
    private String logContent;

    /**
     * 记录人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "记录人 operator字段最大长度50")
    private String operator;

    /**
     * 记录时间
     */
    @Getter@Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime operatorTime;
}
