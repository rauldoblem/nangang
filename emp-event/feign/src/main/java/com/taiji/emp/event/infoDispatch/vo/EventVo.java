package com.taiji.emp.event.infoDispatch.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 事件管理类AcceptVo
 * @author qizhijie-pc
 * @date 2018年10月23日15:24:59
 */
public class EventVo extends BaseVo<String> {

    public EventVo(){}

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
    @Length(max = 50,message = "事件类型名称长度不能超过50")
    private String eventTypeName;

    //事件等级ID
    @Getter@Setter
    @Length(max = 36,message = "事件等级ID长度不能超过36")
    private String eventGradeId;
    //事件等级名称
    @Getter@Setter
    @Length(max = 50,message = "事件等级名称长度不能超过50")
    private String eventGradeName;

    //报告单位ID
    @Getter@Setter
    @Length(max = 36,message = "报告单位ID长度不能超过36")
    private String reportOrgId;

    //报送单位名称
    @Getter@Setter
    @Length(max = 100,message = "报送单位名称长度不能超过100")
    private String reportOrgName;

    //上报人
    @Getter@Setter
    @Length(max = 20,message = "上报人长度不能超过20")
    private String reporter;

    //上报人联系电话
    @Getter@Setter
    @Length(max = 16,message = "上报人联系电话长度不能超过16")
    private String reporterTel;

    //上报方式ID
    @Getter@Setter
    @NotEmpty(message = "上报方式ID不能为空")
    @Length(max = 36,message = "上报方式ID长度不能超过36")
    private String methodId;
    //上报方式名称
    @Getter@Setter
    @Length(max = 50,message = "上报方式名称长度不能超过50")
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
    @Length(max = 1000,message = "事发原因长度不能超过1000")
    private String eventCause;

    //详细描述
    @Getter@Setter
    @Length(max = 4000,message = "详细描述长度不能超过4000")
    private String eventDesc;

    //详细描述
    @Getter@Setter
    @Length(max = 2000,message = "详细描述长度不能超过2000")
    private String protreatment;

    //死亡人数
    @Getter@Setter
    @Min(0)
    private Integer deathNum;

    //受伤人数
    @Getter@Setter
    @Min(0)
    private Integer wondedNum;

    //支援请求
    @Getter@Setter
    @Length(max = 2000,message = "支援请求长度不能超过2000")
    private String request;

    //处置状态：0处置中 1处置结束  2 已评估 3 已归档
    @Getter@Setter
    @Length(max = 1,message = "处置状态长度不能超过1")
    private String handleFlag;

    //处置开始时间(yyyy-MM-dd HH:mm:ss)
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
    private LocalDateTime hBeginTime;

    //处置结束时间(yyyy-MM-dd HH:mm:ss)
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
    private LocalDateTime hEndTime;

    //合成事件部门ID
    @Getter@Setter
    private String createOrgId;

}
