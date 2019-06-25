package com.taiji.emp.duty.vo.dailyShift;

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
 * 交接班跟值班日志  管理实体类 DailyLogShiftVo
 * @author SunYi
 * @date 2018年10月19日
 */
public class DailyLogShiftVo extends IdVo<String> {

    public DailyLogShiftVo(){}

    /**
     * 交接班ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "交接班ID dailyShiftId 字段最大长度36")
    private String dailyShiftId;

    /**
     * 值班日志ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "值班日志ID dailyLogId 字段最大长度36")
    private String dailyLogId;

    /**
     * 日志类型ID
     */
    @Getter
    @Setter
    private String affirtTypeId;

    /**
     * 日志类型名称
     */
    @Getter
    @Setter
    private String affirtTypeName;

    /**
     * 日志内容
     */
    @Getter
    @Setter
    private String logContent;

    /**
     * 日志办理状态码：0为待办，1为办理中，2为办结
     */
    @Getter
    @Setter
    private String treatStatus;

    /**
     * 日志办理时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime treatTime;

    /**
     * 录入人
     */
    @Getter
    @Setter
    private String inputerName;

}
