package com.taiji.emp.nangang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "IF_WEATHER")
@Getter
@Setter
@NoArgsConstructor
public class Weather implements DelFlag {

    @Id
    @GenericGenerator(
            name = "customUUIDGenerator",
            strategy = "com.taiji.micro.common.id.CustomSortUUIDGenerator"
    )
    @GeneratedValue(
            generator = "customUUIDGenerator"
    )
    public String orderFlag;

    @Length(max =50,message = "日期（18日星期日）字段最大长度50")
    private String weatherDate;

    @Length(max =50,message = "空气湿度字段最大长度50")
    private String humidity;

    @Length(max =50,message = "空气温度字段最大长度50")
    private String temperature;

    @Length(max =50,message = "最高气温字段最大长度50")
    private String high;

    @Length(max =50,message = "最低气温字段最大长度50")
    private String low;

    @Length(max =50,message = "风向字段最大长度50")
    private String aqi;

    @Length(max =50,message = "风向字段最大长度50")
    private String windDirection;

    @Length(max =50,message = "风力字段最大长度50")
    private String windPower;

    @Length(max =50,message = "天气类型字段最大长度50")
    private String weatherType;

    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
