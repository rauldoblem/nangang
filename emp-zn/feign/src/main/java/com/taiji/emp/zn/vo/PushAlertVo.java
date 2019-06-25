package com.taiji.emp.zn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author yhcookie
 * @date 2018/12/22 18:02
 */
@Setter
@Getter
@NoArgsConstructor
public class PushAlertVo {

    @Length(max = 50,message = "预警信息来源ID（2：天气预警；3：设备预警）字段最大长度50")
    private String source;

    @Length(max = 50,message = "预警信息的标题字段最大长度50")
    private String headline;

    @Length(max = 50,message = "事件类型ID（10010：台风、暴雨气象灾害；10006：大坝险情字段最大长度50")
    private String eventTypeId;

    @Length(max = 50,message = "事件类型名称字段最大长度50")
    private String eventTypeName;

    @Length(max = 50,message = "预警级别ID（1：红色预警；2：橙色预警；3：黄色预警；4：蓝色预警；9：未知）最大长度50")
    private String severityId;

    @Length(max = 50,message = "预警级别名称字段最大长度50")
    private String severityName;

    @Length(max = 255,message = "预警信息的正文字段最大长度255")
    private String description;

    @Length(max = 50,message = "预警信息相关编码（台风编码、大坝编码）字段最大长度50")
    private String relateCode;

    @Length(max = 50,message = "对建议采取措施的描述字段最大长度50")
    private String instruction;

    @Length(max = 50,message = "对突发事件影响区域的文字描述最大长度50")
    private String areaDesc;

    @Length(max = 50,message = "预警信息发布区域的行政区划代码字段最大长度50")
    private String geoCode;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    private LocalDateTime sendTime;

    @Length(max = 50,message = "预警处理状态最大长度50")
    private String alertStatus;

    @Length(max = 50,message = "设备所属的组织机构代码字段最大长度50")
    private String deviceOrgId;

    @Length(max = 50,message = "设备所属的组织机构名称字段最大长度50")
    private String deviceOrgName;

}
