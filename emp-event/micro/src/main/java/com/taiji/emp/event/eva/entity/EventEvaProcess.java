package com.taiji.emp.event.eva.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 * 过程在线管理实体类 EventEvaProcess
 * @author SunYi
 * @date 2018年10月30日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SE_PROCESS")
public class EventEvaProcess extends BaseEntity<String> implements DelFlag {

    public EventEvaProcess(){}


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
    @NotBlank(message = "所属一级阶段编码 不能为空字符串")
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
    @NotBlank(message = "所属二级阶段名称 不能为空字符串")
    @Column(name = "SECOND_NODE_CODE")
    @Length(max = 36,message = "所属二级节点编码 secondNCode 字段最大长度36")
    private String secNodeCode;

    /**
     * 所属二级节点名称
     * SECOND_N_NAME
     */
    @Getter
    @Setter
    @NotBlank(message = "所属二级阶段名称 不能为空字符串")
    @Column(name = "SECOND_NODE_NAME")
    @Length(max = 50,message = "所属二级阶段名称 secondNName 字段最大长度50")
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
    @NotBlank(message = "过程操作内容 不能为空字符串")
    @Length(max = 4000,message = "过程操作内容 operation 字段最大长度4000")
    private String operation;
    /**
     * 操作人
     */
    @Getter
    @Setter
    @NotBlank(message = "操作人 不能为空字符串")
    @Length(max = 20,message = "操作人 operator 字段最大长度20")
    private String operator;

    /**
     * 操作日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime operationTime;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
