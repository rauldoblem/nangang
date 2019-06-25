package com.taiji.emp.duty.vo.dailylog;

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

public class DailyLogTreatmentVo extends IdVo<String> {

    public DailyLogTreatmentVo() {}

    /**
     * 值班日志ID
     */
    @Getter
    @Setter
    private String dLogId;

    @Getter
    @Setter
    private DailyLogVo dailyLog;

    /**
     * 值班日志办理情况
     */
    @Getter
    @Setter
    @Length(max = 500,message = "值班日志办理情况 dlogTreatment字段最大长度500")
    private String dlogTreatment;

    /**
     * 办理人
     */
    @Getter
    @Setter
    @Length(max = 20,message = "办理人 treatBy字段最大长度20")
    private String treatBy;

    /**
     * 办理时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime treatTime;

    /**
     * 日志办理状态码：0为待办，1为办理中，2为办结
     */
    @Getter
    @Setter
    @Length(max = 1,message = "日志办理状态码 treatStatus字段最大长度1")
    private String treatStatus;
}
