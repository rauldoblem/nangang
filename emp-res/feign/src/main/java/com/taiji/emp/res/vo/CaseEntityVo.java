package com.taiji.emp.res.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 *  案例管理实体类 CaseVo
 * @author SunYi
 * @date 2018年11月2日
 */
public class CaseEntityVo extends BaseVo<String> {

    public CaseEntityVo(){}

    /**
     * 案例名称
     * TITLE
     */
    @Getter
    @Setter
    @Length(max = 100,message = "案例名称 title字段最大长度100")
    private String title;

    /**
     * 归档事件ID
     * EVENT_ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "归档事件ID eventId 字段最大长度36")
    private String eventId;


    /**
     * 事件名称
     * EVENT_NAME
     */
    @Getter
    @Setter
    @Length(max = 100,message = "事件名称 eventName 字段最大长度100")
    private String eventName;


    /**
     * 事发地点
     * POSITION
     */
    @Getter
    @Setter
    @Length(max = 80,message = "事发地点 position 字段最大长度80")
    private String position;

    /**
     * 事发时间
     *  OCCUR_TIME
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime occurTime;

    /**
     * EVENT_TYPE_ID
     * 事件类型ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "事件类型ID name字段最大长度36")
    private String eventTypeId;


    /**
     * EVENT_TYPE_NAME
     * 事件类型NAME
     */
    @Getter
    @Setter
    @Length(max = 50,message = "事件类型ID eventTypeName 字段最大长度50")
    private String eventTypeName;

    /**
     * 事件等级ID
     * EVENT_GRADE_ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "事件等级ID eventGradeId  字段最大长度1")
    private String eventGradeId;

    /**
     * 事件等级名称
     * EVENT_GRADE_NAME
     */
    @Getter
    @Setter
    @Length(max = 36,message = "事件等级名称 eventGradeName  字段最大长度1")
    private String eventGradeName;


    /**
     * SOURCE_FLAG
     * 案例来源标识码（0：系统归档，1：系统录入）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "案例来源标识码 sourceFlag 字段最大长度1")
    private String sourceFlag;

    /**
     * CASE_SOURCE
     * 案例来源  案例来源标识码为1，由用户输入。为0 存事件归档
     */
    @Getter
    @Setter
    @Length(max = 50,message = "案例来源 caseSource 字段最大长度50")
    private String caseSource;

    /**
     * DESCRIBES
     * 案例描述
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "案例描述 describes 字段最大长度2000")
    private String describes;


    /**
     * NOTES 备注
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "备注 notes 字段最大长度2000")
    private String notes;

    /**
     * 创建单位ID
     * CREATE_ORG_ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "创建单位ID createOrgId 字段最大长度36")
    private String createOrgId;

    /**
     * 创建单位名称
     * CREATE_ORG_NAME
     */
    @Getter
    @Setter
    @Length(max = 100,message = "创建单位名称 createOrgName 字段最大长度100")
    private String createOrgName;

}
