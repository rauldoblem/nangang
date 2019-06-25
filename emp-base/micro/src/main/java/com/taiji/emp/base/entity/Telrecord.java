package com.taiji.emp.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_TELRECORD")
public class Telrecord extends BaseEntity<String> implements DelFlag {
    public Telrecord(){}
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
    private String filePath;
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
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
