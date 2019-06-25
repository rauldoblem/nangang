package com.taiji.emp.duty.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 值班表 Scheduling
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_SCHEDULING")
public class Scheduling extends IdEntity<String> {

    public Scheduling() {}

    /**
     *  所属单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     *  所属单位名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "所属单位名称 orgName字段最大长度50")
    private String orgName;

    /**
     *  值排班日期(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter
    @Setter
    private LocalDate dutyDate;

    /**
     *  日期类型（1：工作日；2：双休日，3：法定节假日，4：特殊节假日，5：其它）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "日期类型 dateTypeCode字段最大长度1")
    @Column(name = "D_TYPE_CODE")
    private String dateTypeCode;


    /**
     *  日期类型名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "日期类型名称 dateTypeName字段最大长度20")
    @Column(name = "D_TYPE_NAME")
    private String dateTypeName;

    /**
     *  节假日名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "节假日名称 holidayName字段最大长度50")
    private String holidayName;

    /**
     *  班次ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "班次ID shiftPatternId字段最大长度36")
    @Column(name = "SHIFT_PATT_ID")
    private String shiftPatternId;

    /**
     *  班次名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "班次名称 shiftPatternName字段最大长度20")
    @Column(name = "SHIFT_PATT_NAME")
    private String shiftPatternName;

    /**
     *  值班分组ID
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = DutyTeam.class)
    @JoinColumn(name = "DUTY_TEAM_ID",referencedColumnName = "ID")
    private DutyTeam dutyTeamId;

    /**
     *  值班分组名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "值班分组名称 dutyTeamName字段最大长度20")
    @Column(name = "DUTY_TEAM_NAME")
    private String dutyTeamName;

    /**
     *  值班分组的值班类型编码（0：按班次值班，1：按天值班）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "值班分组的值班类型编码 perTypeName字段最大长度1")
    @Column(name = "PTYPE_CODE")
    private String ptypeCode;

    /**
     *  值班人员ID
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "PERSON_ID",referencedColumnName = "ID")
    private Person person;

    /**
     *  值班人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "值班人员姓名 personName字段最大长度50")
    private String personName;

    /**
     *  历史值班人员ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "历史值班人员ID hisPersonId字段最大长度36")
    @Column(name = "HIS_PERSON_ID")
    private String hisPersonId;

    /**
     *  历史值班人员姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "历史值班人员姓名 hisPersonName字段最大长度50")
    @Column(name = "HIS_PERSON_NAME")
    private String hisPersonName;

    /**
     *  值班开始时间 (yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime startTime;

    /**
     *  值班结束时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime endTime;

    /**
     * 统计数量
     */
    @Getter
    @Setter
    @Transient
    private Long number;

//    /**
//     * 统计类型数量
//     */
//    @Getter
//    @Setter
//    private String dateTypeCode;

}
