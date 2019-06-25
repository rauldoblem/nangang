package com.taiji.emp.event.cmd.searchVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 任务信息时间轴Vo
 * @author SunYi
 */
public class EcTaskListVo {

    /**下发时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    @Getter@Setter
    private LocalDate taskDate;

    /**
     * TaskVo
     */
    @Getter
    @Setter
    private List<EcTaskVo> tasks;

}
