package com.taiji.emp.event.eva.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 事件评估项目 实体类 EventEvaItem
 * @author qzp-pc
 * @date 2018年11月06日15:27:18
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SE_EVENT_EVA_ITEM")
public class EventEvaItem extends BaseEntity<String> implements DelFlag {

    public EventEvaItem() {}

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

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
