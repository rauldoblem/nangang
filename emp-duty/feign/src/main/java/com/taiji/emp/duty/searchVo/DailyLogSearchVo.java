package com.taiji.emp.duty.searchVo;

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

public class DailyLogSearchVo extends BasePageVo {

    @Getter
    @Setter
    private String isLastlyFlag;

    @Getter
    @Setter
    private List<String> affirtTypeIds;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime nowTime;

    @Getter
    @Setter
    private List<String> treatStatuses;

    @Getter
    @Setter
    private String orgId;

    /**
     * 录入人
     */
    @Getter
    @Setter
    private String inputerName;
}
