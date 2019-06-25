package com.taiji.emp.zn.vo;

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
 * 预警/事件信息vo对象 --InfoStatVo
 */
public class InfoStatVo extends IdVo<String>{

    public InfoStatVo(){}

    @Getter@Setter
    private String title;

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
    private LocalDateTime reportTime;

    @Getter@Setter
    private String infotype;

    @Getter@Setter
    @Length(max = 36,message = "eventTypeId 字段最大长度为36")
    private String eventTypeId;

    @Getter@Setter
    @Length(max = 50,message = "eventTypeName 字段最大长度为50")
    private String eventTypeName;
}
