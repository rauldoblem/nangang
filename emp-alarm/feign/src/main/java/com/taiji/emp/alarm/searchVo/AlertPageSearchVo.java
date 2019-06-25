package com.taiji.emp.alarm.searchVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预警信息分页查询包装类
 */
public class AlertPageSearchVo extends BasePageVo{

    public AlertPageSearchVo(){}

    /**
     * 预警信息的标题
     */
    @Getter@Setter
    private String headline;

    /**
     * 预警事件的严重程度（预警级别）Id
     */
    @Getter@Setter
    private String severityId;

    /**
     * 预警类型IDs -- 同事件类型
     */
    @Getter@Setter
    private List<String> eventTypeIds;

    /**
     * 预警信息来源（1：系统录入，2：天气预警，3：设备预警）
     */
    @Getter@Setter
    private String source;

    /**
     * 发布时间查询开始时间
     */
    @Getter@Setter
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    private LocalDateTime sendStartTime;

    /**
     * 发布时间查询结束时间
     */
    @Getter@Setter
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    private LocalDateTime sendEndTime;

    /**
     * 待办：（0：未处理），已办：（1：已忽略；2：已通知；3：已办结）
     */
    @Getter@Setter
    private List<String> noticeFlags;

    /**
     * 单位ID，即当前用户所属单位ID(发送方过滤使用)
     */
    @Getter@Setter
    private String orgId;

}
