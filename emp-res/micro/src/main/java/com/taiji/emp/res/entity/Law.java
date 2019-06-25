package com.taiji.emp.res.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "KL_LAWS")
public class Law extends BaseEntity<String> implements DelFlag {
    public Law(){}
    @Getter
    @Setter
    @Length(max = 100,message = "法规名称title字段最大长度100")
    private String title;
    @Getter
    @Setter
    @Length(max = 50,message = "制定单位buildOrg字段最大长度50")
    private String buildOrg;
    @Getter
    @Setter
    @Length(max = 36,message = "法规类型IDlawTypeId字段最大长度36")
    private String lawTypeId;
    @Getter
    @Setter
    @Length(max = 50,message = "法规类型名称lawTypeName字段最大长度50")
    private String lawTypeName;
    @Getter
    @Setter
    @Length(max = 36,message = "事件类型IDeventTypeId字段最大长度36")
    private String eventTypeId;
    @Getter
    @Setter
    @Length(max = 50,message = "事件类型名称eventTypeName字段最大长度50")
    private String eventTypeName;
    @Getter
    @Setter
    @Length(max = 100,message = "适用范围appliedRange字段最大长度100")
    private String appliedRange;
    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    private LocalDate pubTime;
    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using= LocalDateSerializer.class)
    private LocalDate inureTime;
    @Getter
    @Setter
    @Length(max = 100,message = "关键字keyWord字段最大长度100")
    private String keyWord;
    @Getter
    @Setter
    @Length(max = 100,message = "摘要summary字段最大长度100")
    private String summary;
    @Getter
    @Setter
    private String content;
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;


}