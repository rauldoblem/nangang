package com.taiji.emp.event.eva.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class EventEvaReportDataVo extends BaseVo<String> {

    public EventEvaReportDataVo() {}

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
    private String overallEva;

    /**
     * 评估人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "评估人 evaLuator字段最大长度50")
    private String evaluator;

    /**
     * 评估单位
     */
    @Getter
    @Setter
    @Length(max = 100,message = "评估单位 evaLuateUnit字段最大长度100")
    private String evaluateUnit;

    /**
     * 评估时间(yyyy-MM-dd)
     */
    @Getter@Setter
    private String evaluateTime;

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

    @Getter
    @Setter
    private List<EventEvaScoreVo> evaScore;


}
