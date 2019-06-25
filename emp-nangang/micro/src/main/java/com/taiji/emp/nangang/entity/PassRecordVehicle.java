package com.taiji.emp.nangang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author yhcookie
 * @date 2018/11/30 13:44
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "VW_NG_PASS_RECORD_VEHICLE")
public class PassRecordVehicle {

    @Id
    @GenericGenerator(
            name = "customUUIDGenerator",
            strategy = "com.taiji.micro.common.id.CustomSortUUIDGenerator"
    )
    @GeneratedValue(
            generator = "customUUIDGenerator"
    )
    @Length(max = 36,message = "ID字段最大长度36")
    public String id;

    @Length(max =50,message = "车辆类型字段最大长度50")
    private String vehicleType;

    /**
     * inOutFlag 入：1  出：0
     */
    @Length(max = 1,message = "出入标识字段最大长度1")
    private String inOutFlag;

    @Length(max = 36,message = "通道字段最大长度36")
    private String hongqiRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String haigangSouthRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String haigangNorthRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String nantiRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String haifangNorthRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String haifangMiddleRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String haifangSouthRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String chuangyeRoad;

    @Length(max = 36,message = "通道字段最大长度36")
    private String binhaiNorthRoad;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @Length(max = 36,message = "车辆总数最大长度36")
    private String totalVehicle;

}
