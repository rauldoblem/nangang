package com.taiji.emp.duty.vo.dailylog;

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
import java.util.List;

/**
 * 值班日志实体类 feign DailyLogVo
 * @author qzp-pc
 * @date 2018年10月28日10:56:03
 */
public class DailyLogVo extends BaseVo<String> {

    public DailyLogVo() {}

    /**
     * 日志类型ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "日志类型ID affirtTypeId字段最大长度36")
    private String affirtTypeId;

    /**
     * 日志类型名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "日志类型名称 affirtTypeName字段最大长度50")
    private String affirtTypeName;

    /**
     * 标注提示名称：0，重要；1，一般
     */
    @Getter
    @Setter
    @Length(max = 20,message = "标注提示名称 emeGradeFlag字段最大长度20")
    private String emeGradeFlag;

    /**
     * 日志办理状态码：0为待办，1为办理中，2为办结
     */
    @Getter
    @Setter
    @Length(max = 1,message = "日志办理状态码 treatStatus字段最大长度1")
    private String treatStatus;

    /**
     * 日志办理时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime treatTime;

    /**
     * 日志内容
     */
    @Getter
    @Setter
    @Length(max = 1000,message = "日志内容 logContent字段最大长度1000")
    private String logContent;

    /**
     * 所属部门ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所属部门ID orgId字段最大长度36")
    private String orgId;

    /**
     * 所属部门名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "所属部门名称orgName字段最大长度50")
    private String orgName;

    /**
     * 录入人ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "录入人ID inputerId字段最大长度36")
    private String inputerId;

    /**
     * 录入人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "录入人inputerName字段最大长度50")
    private String inputerName;

    /**
     * 值班日志办理信息列表
     */
    @Getter
    @Setter
    private List<DailyLogTreatmentVo> dLogTreatment;
}
