package com.taiji.emp.event.eva.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 事件评估分值 实体类 feign EventEvaScoreVo
 * @author qzp-pc
 * @date 2018年11月06日17:00:18
 */
public class EventEvaScoreVo extends IdVo<String> {

    public EventEvaScoreVo() {}

    /**
     * 事件报告ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "事件报告ID reportId字段最大长度36")
    private String reportId;

    /**
     * 评估项ID
     */
    @Getter
    @Setter
    private EventEvaItemVo eventEvaItem;


    /**
     * 评估成绩
     */
    @Getter
    @Setter
    private Double score;
}
