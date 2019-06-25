package com.taiji.emp.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class TelrecordVo extends BaseVo<String> {
    public TelrecordVo(){}
    @Getter
    @Setter
    @Length(max = 16,message = "主叫号码caller字段最大长度16")
    private String caller;
    @Getter
    @Setter
    @Length(max = 16,message = "被叫号码callee字段最大长度16")
    private String callee;
    @Getter
    @Setter
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime callBeginTime;
    @Getter
    @Setter
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime callEndTime;
    @Getter
    @Setter
    @Length(max = 1000,message = "文件地址fileSeq字段最大长度1000")
    private String fileSeq;
    @Getter
    @Setter
    @Length(max = 36,message = "通话时长callDuration字段最大长度36")
    private String callDuration;
    @Getter
    @Setter
    @Length(max = 50,message = "文件大小fileSize字段最大长度50")
    private String fileSize;
    @Getter
    @Setter
    @Length(max = 50,message = "文件名称fileName字段最大长度50")
    private String fileName;
}
