package com.taiji.emp.duty.vo.dailyShift;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 交接班 管理实体类Vo DailyShiftVo
 * @author SunYi
 * @date 2018年10月19日
 */
public class DailyShiftVo extends IdVo<String> {

    public DailyShiftVo(){}


    /**
     * 标题
     */
    @Getter
    @Setter
    @Length(max = 100,message = "标题 title 字段最大长度100")
    private String title;
    /**
     * 交班人ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "交班人ID fromWatcherId 字段最大长度36")
    private String fromWatcherId;

    /**
     * 交班人姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "交班人姓名 fromWatcherName 字段最大长度50")
    private String fromWatcherName;

    /**
     * 接班人ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "接班人ID toWatcherId 字段最大长度36")
    private String toWatcherId;

    /**
     * 接班人姓名
     */
    @Getter
    @Setter
    @Length(max = 50,message = "接班人姓名 toWatcherName 字段最大长度50")
    private String toWatcherName;

    /**
     * 备注信息
     */
    @Getter
    @Setter
    @Length(max = 500,message = "备注信息 notes 字段最大长度500")
    private String notes;

    /**
     * 创建单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "创建单位ID createOrgId 字段最大长度36")
    private String createOrgId;

    /**
     * 创建单位名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "创建单位名称 createOrgName 字段最大长度50")
    private String createOrgName;
    /**
     * 创建人姓名
     */
    @Getter
    @Setter
    @Length(max = 20,message = "创建人姓名 createOrgName 字段最大长度20")
    private String createBy;

    /**
     * 创建日期
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @CreatedDate
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @Getter@Setter
    private LocalDateTime createTime;

    @Getter@Setter
    private List<DailyLogShiftVo> dailyLogShift;

}
