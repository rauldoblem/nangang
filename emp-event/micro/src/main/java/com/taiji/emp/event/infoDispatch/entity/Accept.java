package com.taiji.emp.event.infoDispatch.entity;


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
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 报送信息类Accept
 * @author qizhijie-pc
 * @date 2018年10月23日15:24:59
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "IM_ACCEPT")
public class Accept extends BaseEntity<String> implements DelFlag{

    public Accept(){}

    //接报名称
    @Getter@Setter
    @NotEmpty(message = "接报名称不能为空")
    @Length(max = 200,message = "接报名称长度不能超过200")
    private String eventName;

    //事发地点
    @Getter@Setter
    @NotEmpty(message = "事发地点不能为空")
    @Length(max = 200,message = "事发地点长度不能超过200")
    private String position;

    //经纬度
    @Getter@Setter
    @NotEmpty(message = "经纬度不能为空")
    @Length(max = 64,message = "经纬度长度不能超过64")
    private String lonAndLat;

    //事发时间
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
    @Getter@Setter
    @NotNull(message = "事发时间不能为空")
    private LocalDateTime occurTime;

    //事件类型ID
    @Getter@Setter
    @NotEmpty(message = "事件类型ID不能为空")
    @Length(max = 36,message = "事件类型ID长度不能超过36")
    private String eventTypeId;

    //事件类型名称
    @Getter@Setter
    @NotEmpty(message = "事件类型名称不能为空")
    @Length(max = 100,message = "事件类型名称长度不能超过100")
    private String eventTypeName;

    //事件等级ID
    @Getter@Setter
    @Length(max = 36,message = "事件等级ID长度不能超过36")
    private String eventGradeId;

    //事件等级名称
    @Getter@Setter
    @Length(max = 50,message = "事件等级名称长度不能超过50")
    private String eventGradeName;

    //报送单位ID
    @Getter@Setter
    @NotEmpty(message = "报送单位ID不能为空")
    @Length(max = 36,message = "报送单位ID长度不能超过36")
    private String reportOrgId;

    //报送单位名称
    @Getter@Setter
    @NotEmpty(message = "报送单位名称不能为空")
    @Length(max = 100,message = "报送单位名称长度不能超过100")
    private String reportOrgName;

    //上报人
    @Getter@Setter
    @NotEmpty(message = "上报人不能为空")
    @Length(max = 50,message = "上报人长度不能超过50")
    private String reporter;

    //上报人联系电话
    @Getter@Setter
    @NotEmpty(message = "上报人联系电话不能为空")
    @Length(max = 16,message = "上报人联系电话长度不能超过16")
    private String reporterTel;

    //上报方式ID
    @Getter@Setter
    @NotEmpty(message = "上报方式ID不能为空")
    @Length(max = 36,message = "上报方式ID长度不能超过36")
    @Column(name = "R_METHOD_ID")
    private String methodId;

    //上报方式名称
    @Getter@Setter
    @Length(max = 50,message = "上报方式名称长度不能超过50")
    @Column(name = "R_METHOD_NAME")
    private String methodName;

    //上报时间
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
    @Getter@Setter
    private LocalDateTime reportTime;

    //事发原因
    @Getter@Setter
    @Length(max = 500,message = "事发原因长度不能超过500")
    private String eventCause;

    //详细描述
    @Getter@Setter
    @Length(max = 2000,message = "详细描述长度不能超过2000")
    private String eventDesc;

    //已采取措施
    @Getter@Setter
    @Length(max = 2000,message = "已采取措施长度不能超过2000")
    private String protreatment;

    //死亡人数
    @Getter@Setter
    @Min(0)
    private int deathNum;

    //受伤人数
    @Getter@Setter
    @Min(0)
    @Column(name="WOUNDED_NUM")
    private int wondedNum;

    //支援请求
    @Getter@Setter
    @Length(max = 2000,message = "支援请求长度不能超过2000")
    private String request;

    //是否初报 0是，1否
    @Getter@Setter
    @NotEmpty(message = "是否初报不能为空")
    @Length(max = 1,message = "是否初报长度不能超过1")
    @Column(name = "ISFIRST")
    private String isFirst;

    //初报ID
    @Getter@Setter
    @Length(max = 2000,message = "初报ID长度不能超过50")
    private String firstReportId;

    //创建单位ID
    @Getter@Setter
    @Length(max = 2000,message = "创建单位ID长度不能超过50")
    private String createOrgId;

    //创建单位名称(临时变量)
    @Getter@Setter
    @Transient
    private String createOrgName;

    //创建用户Id(临时变量)
    @Getter@Setter
    @Transient
    private String createUserId;

    /**
     * 删除标志
     */
    @Getter@Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    @Getter@Setter
    @Transient
    private String dealId;

}
