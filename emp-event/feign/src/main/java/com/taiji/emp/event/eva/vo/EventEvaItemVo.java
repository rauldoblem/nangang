package com.taiji.emp.event.eva.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 事件评估项目 实体类 feign EventEvaItemVo
 * @author qzp-pc
 * @date 2018年11月06日17:02:18
 */
public class EventEvaItemVo extends BaseVo<String> {

    public EventEvaItemVo() {}

    /**
     * 适用单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "适用单位ID orgId字段最大长度36")
    private String orgId;

    /**
     * 事件类型ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "事件类型ID eventTypeId字段最大长度36")
    private String eventTypeId;

    /**
     * 事件类型名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "事件类型名称 eventTypeName字段最大长度100")
    private String eventTypeName;

    /**
     * 评估项
     */
    @Getter
    @Setter
    @Length(max = 100,message = "评估项 name字段最大长度100")
    private String name;

    /**
     * 评估项说明
     */
    @Getter
    @Setter
    @Length(max = 300,message = "评估项说明 interpret字段最大长度300")
    private String interpret;

    /**
     * 权重
     */
    @Getter
    @Setter
    private Double proportion;
}
