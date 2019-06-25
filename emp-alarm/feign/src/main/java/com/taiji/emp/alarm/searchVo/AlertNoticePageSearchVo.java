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
 * 预警通知信息分页查询包装类
 */
public class AlertNoticePageSearchVo extends BasePageVo{

    public AlertNoticePageSearchVo(){}

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
     * 通知下发时间查询开始时间
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
     * 通知下发时间查询结束时间
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
     * 接收单位ID，即当前用户所属单位ID(接收方过滤使用)
     */
    @Getter@Setter
    private String revOrgId;

    /**
     * 预警信息Id
     */
    @Getter@Setter
    private String alertId;

}
