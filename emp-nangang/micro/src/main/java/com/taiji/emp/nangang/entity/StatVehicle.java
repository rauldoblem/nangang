package com.taiji.emp.nangang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author yhcookie
 * @date 2018/12/5 10:36
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "NG_STAT_VEHICLE")
public class StatVehicle {
    @Id
    @Length(max = 36,message = "ID字段最大长度36")
    public String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    @Length(max = 36,message = "时间间隔字段最大长度36")
    private String interval;

    @Length(max = 36,message = "卡口编号字段最大长度36")
    private String bayonetId;

    @Length(max = 36,message = "通道口ID字段最大长度36")
    private String channelId;

    @Length(max = 36,message = "车辆类型编号字段最大长度36")
    private String vehicleType;

    @Length(max = 36,message = "企业Id字段最大长度36")
    private String conpanyId;

    @Length(max = 50,message = "车流量字段最大长度36")
    private String count;

    @Length(max = 1,message = "验证类型字段最大长度1")
    private String checkedType;

    @Length(max = 1,message = "通行类型字段最大长度1")
    private String inOutFlag;

    @Length(max = 50,message = "卡口名称字段最大长度50")
    private String bayonetName;

    @Length(max = 50,message = "通行口名称字段最大长度50")
    private String channelName;
}
