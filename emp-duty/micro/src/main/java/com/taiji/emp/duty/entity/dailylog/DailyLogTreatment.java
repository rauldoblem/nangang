package com.taiji.emp.duty.entity.dailylog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 值班日志办理类 DailyLogTreatment
 * @author qzp-pc
 * @date 2018年10月28日10:28:13
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_DAILYLOG_TREATMENT")
public class DailyLogTreatment extends IdEntity<String> {

    public DailyLogTreatment() {}

    /**
     * 值班日志ID
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = DailyLog.class)
    @JoinColumn(name = "DLOG_ID",referencedColumnName = "ID")
    private DailyLog dailyLog;

    /**
     * 值班日志办理情况
     */
    @Getter
    @Setter
    @Length(max = 500,message = "值班日志办理情况 dlogTreatment字段最大长度500")
    private String dlogTreatment;

    /**
     * 办理人
     */
    @Getter
    @Setter
    @Length(max = 20,message = "办理人 treatBy字段最大长度20")
    private String treatBy;

    /**
     * 办理时间(yyyy-MM-dd HH:mm:SS)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime treatTime;

    /**
     * 日志办理状态码：0为待办，1为办理中，2为办结
     */
    @Getter
    @Setter
    @Length(max = 1,message = "日志办理状态码 treatStatus字段最大长度1")
    private String treatStatus;
}
