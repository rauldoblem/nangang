package com.taiji.emp.event.eva.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 过程在线管理v EventEvaProcessVo
 * @author SunYi
 * @date 2018年10月30日
 */
public class EventEvaProcessVo extends BaseVo<String> {

    public EventEvaProcessVo(){}

    /**
     * 事件ID
     */
    @Getter
    @Setter
    @NotBlank(message = "事件ID不能为空字符串")
    @Length(max = 36,message = "事件ID eventId 字段最大长度36")
    private String eventId;

    /**
     * 事件名称
     */
    @Getter
    @Setter
    @NotBlank(message = "事件名称不能为空字符串")
    @Length(max = 200,message = "事件名称 orgId字段最大长度200")
    private String eventName;

    /**
     * 所属一级阶段编码
     *  FIRST_N_CODE
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所属一级阶段编码 firstNCode 字段最大长度36")
    private String firstNodeCode;

    /**
     * 所属一级阶段名称
     * FIRST_N_NAME
     */
    @Getter
    @Setter
    @NotBlank(message = "所属一级阶段名称 不能为空字符串")
    @Length(max = 50,message = "所属一级阶段名称 firstNName 字段最大长度50")
    private String firstNodeName;

    /**
     * 所属二级节点编码
     * SECOND_N_CODE
     */
    @Getter
    @Setter
    @NotBlank(message = "所属一级阶段名称 不能为空字符串")
    @Length(max = 36,message = "所属二级节点编码 secondNCode 字段最大长度36")
    private String secNodeCode;

    /**
     * 所属二级节点名称
     * SECOND_N_NAME
     */
    @Getter
    @Setter
    @NotBlank(message = "所属一级阶段名称 不能为空字符串")
    @Length(max = 50,message = "备注信息 secondNName 字段最大长度50")
    private String secNodeName;

    /**
     * 过程记录类型：0系统自动创建 1人工手动创建
     */
    @Getter
    @Setter
    @Length(max = 1,message = "过程记录类型 processType 字段最大长度1")
    private String processType;

    /**
     * 过程操作内容
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "过程操作内容 operation 字段最大长度4000")
    private String operation;
    /**
     * 操作人
     */
    @Getter
    @Setter
    @Length(max = 20,message = "操作人 operator 字段最大长度20")
    private String operator;

    /**
     * 操作日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime operationTime;

}
