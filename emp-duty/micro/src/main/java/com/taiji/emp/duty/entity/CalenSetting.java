package com.taiji.emp.duty.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;


/**
 * 日历设置表 CalenSetting
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_CALEN_SETTING")
public class CalenSetting extends IdEntity<String> {

    public CalenSetting() {}

    /**
     *  所属单位ID
     */
    @Getter
    @Setter
    @Column(name = "ORG_ID")
    @Length(max = 36,message = "所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     *  所属单位名称
     */
    @Getter
    @Setter
    @Column(name = "ORG_NAME")
    @Length(max = 50,message = "所属单位名称orgName字段最大长度50")
    private String orgName;

    /**
     *  日历日期(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter
    @Setter
    @Column(name = "SETTING_DATE")
    private LocalDate settingDate;

    /**
     *  日期类型编码（1：工作日；2：双休日，3：法定节假日，4：特殊节假日，5：其它）
     */
    @Getter
    @Setter
    @Column(name = "D_TYPE_CODE")
    private String dateTypeCode;

    /**
     *  节假日名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "节假日名称holidayName字段最大长度20")
    @Column(name = "HOLIDAY_NAME")
    private String holidayName;
}
