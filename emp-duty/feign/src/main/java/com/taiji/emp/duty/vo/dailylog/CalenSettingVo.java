package com.taiji.emp.duty.vo.dailylog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * 日历设置表 feign CalenSettingVo
 */
public class CalenSettingVo extends BaseVo<String> {

    public CalenSettingVo() {}

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
    private LocalDate settingDate;

    /**
     *  日期类型编码（1：工作日；2：双休日，3：法定节假日，4：特殊节假日，5：其它）
     */
    @Getter
    @Setter
    @Min(value=0,message = "日期类型编码最小为0")
    @Max(value=9999,message = "日期类型编码最大为9999")
    private Integer dateTypeCode;

    /**
     *  节假日名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "节假日名称holidayName字段最大长度20")
    private String holidayName;

    /**
     *  前台接收日期
     */
    @Getter
    @Setter
    private String month;

    /**
     *  前台天数
     */
    @Getter
    @Setter
    private String day;

    /**
     *  日期
     */
    @Getter
    @Setter
    private String time;


}
