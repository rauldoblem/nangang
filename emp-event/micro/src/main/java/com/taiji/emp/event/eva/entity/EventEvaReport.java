package com.taiji.emp.event.eva.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * 事件评估报告 实体类 EventEvaReport
 * @author qzp-pc
 * @date 2018年11月06日15:27:18
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SE_EVENT_EVA_REPORT")
public class EventEvaReport extends BaseEntity<String> implements DelFlag {

    public EventEvaReport() {}

    /**
     * 事件ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "事件ID eventId字段最大长度36")
    private String eventId;

    /**
     * 报告名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "报告名称 name字段最大长度100")
    private String name;

    /**
     * 总体评价
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "总体评价 overAllEva字段最大长度2000")
    @Column(name = "OVERALL_EVA")
    private String overAllEva;

    /**
     * 评估人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "评估人 evaLuator字段最大长度50")
    @Column(name = "EVALUATOR")
    private String evaLuator;

    /**
     * 评估单位
     */
    @Getter
    @Setter
    @Length(max = 100,message = "评估单位 evaLuateUnit字段最大长度100")
    @Column(name = "EVALUATE_UNIT")
    private String evaLuateUnit;

    /**
     * 评估时间(yyyy-MM-dd)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter@Setter
    @Column(name = "EVALUATE_TIME")
    private LocalDate evaLuateTime;

    /**
     * 总成绩
     */
    @Getter
    @Setter
    private Double totalScore;

    /**
     * 结果等级
     */
    @Getter
    @Setter
    @Length(max = 20,message = "结果等级 resultGrade字段最大长度20")
    private String resultGrade;

    /**
     * 建议
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "建议 advice字段最大长度2000")
    private String advice;

    /**
     * 建议
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "建议 notes字段最大长度2000")
    private String notes;

    /*@Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "eventEvaReport",targetEntity = EventEvaScore.class)
    private List<EventEvaScore> evaScore;*/

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
