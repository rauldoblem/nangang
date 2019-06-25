package com.taiji.emp.alarm.entity;

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
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 预警信息实体类 Alert
 * @author qizhijie-pc
 * @date 2018年12月12日17:29:24
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "MA_ALERT")
public class Alert extends BaseEntity<String> implements DelFlag{

    public Alert(){}

    /**
     * 预警信息来源（1：系统录入，2：天气预警，3：设备预警） -- 枚举类
     */
    @Getter@Setter
    @Length(max =1,message = "source 最大长度不能超过1")
    @NotEmpty(message = "source 不能为空字符串")
    private String source;

    /**
     * 预警信息的标题
     */
    @Getter@Setter
    @Length(max =200,message = "headline 最大长度不能超过200")
    @NotEmpty(message = "headline 不能为空字符串")
    private String headline;

    /**
     * 预警信息类型：1（Alert首次）首次发布的预警；
     * 2(Update更新)更新预警信息（续发属于更新）；
     * 3(Cancel解除)一次预警事件生命周期结束后，可对其进行解除；
     * 8（Ack确认）确认收到的预警信息；
     * 9（Error错误）表示拒绝接收到的预警信息。
     * -- 使用枚举类
     * -- 暂时未有值
     */
    @Getter@Setter
    @Length(max =1,message = "msgtype 最大长度不能超过1")
    private String msgtype;

    /**
     * 预警类型ID -- 同事件类型
     */
    @Getter@Setter
    @Length(max =36,message = "eventTypeId 最大长度不能超过36")
    private String eventTypeId;

    /**
     * 预警类型名称
     */
    @Getter@Setter
    @Length(max =100,message = "eventTypeName 最大长度不能超过100")
    private String eventTypeName;

    /**
     * 预警事件的严重程度（预警级别）Id -- 数据字典
     */
    @Getter@Setter
    @Length(max =36,message = "severityId 最大长度不能超过36")
    private String severityId;

    /**
     * 预警事件的严重程度（预警级别）名称
     * 1(红色预警/I级/特别重大)；
     * 2(橙色预警/II级/重大)；
     * 3(黄色预警/III/较大)；
     * 4(蓝色预警/IV/一般)；
     * 9(未知,9)
     */
    @Getter@Setter
    @Length(max =100,message = "eventTypeName 最大长度不能超过100")
    private String severityName;

    /**
     * 预警内容
     */
    @Getter@Setter
    @Length(max =2000,message = "description 最大长度不能超过2000")
    @NotEmpty(message = "description 不能为空字符串")
    private String description;

    /**
     * 对建议采取措施的描述
     */
    @Getter@Setter
    @Length(max =1000,message = "instruction 最大长度不能超过1000")
    private String instruction;

    /**
     * 对突发事件影响区域的文字描述
     */
    @Getter@Setter
    @Length(max =500,message = "areaDesc 最大长度不能超过500")
    private String areaDesc;

    /**
     * 预警信息发布区域的行政区划代码
     */
    @Getter@Setter
    @Length(max =1000,message = "geoCode 最大长度不能超过1000")
    private String geoCode;

    /**
     * 预警信息的发布时间 -- 页面填写
     */
    @Getter@Setter
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

    /**
     * 预警信息生效时间 -- 页面填写
     */
    @Getter@Setter
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
    private LocalDateTime effective;

    /**
     * 对预警信息解除原因的说明 -- 暂时未有值
     */
    @Getter@Setter
    @Length(max =1000,message = "note 最大长度不能超过1000")
    private String note;

    /**
     * 预警处理状态（0：未处理；1：已忽略；2：处置中；3：已办结）  -- 常量
     */
    @Getter@Setter
    @Length(max =1000,message = "alertStatus 最大长度不能超过1000")
    private String alertStatus;

    /**
     * 创建单位ID
     */
    @Getter@Setter
    @Length(max =36,message = "createOrgId 最大长度不能超过1000")
    private String createOrgId;

    /**
     * 创建单位名称
     */
    @Getter@Setter
    @Length(max =100,message = "createOrgName 最大长度不能超过100")
    private String createOrgName;

    /**
     * 由预警信息生成的事件ID -- 暂未有值
     */
    @Getter@Setter
    @Length(max =36,message = "eventId 最大长度不能超过36")
    private String eventId;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 是否通知（0：已通知；1：未通知）
     */
    @Getter@Setter
    @Length(max =1,message = "isNotice 最大长度不能超过1")
    private String isNotice;
}
