package com.taiji.emp.nangang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_DAILYCHECK")
@Getter
@Setter
@NoArgsConstructor
public class DailyCheck extends BaseEntity<String> implements DelFlag {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate checkDate;

    @Length(max =50,message = "检查班次checkShiftPattern字段最大长度50")
    private String shiftPatternName;

    @Length(max =1,message = "是否交接isShift字段最大长度1")
    private String isShift;

    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    @Length(max =36,message = "检查班次Id字段最大长度36")
    private String shiftPatternId;
}
