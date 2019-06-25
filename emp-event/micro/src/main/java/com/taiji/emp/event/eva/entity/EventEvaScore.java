package com.taiji.emp.event.eva.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

/**
 * 事件评估分值 实体类 EventEvaReport
 * @author qzp-pc
 * @date 2018年11月06日15:27:18
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SE_EVENT_EVA_SCORE")
public class EventEvaScore extends IdEntity<String> {

    public EventEvaScore() {}

    /**
     * 事件报告ID
     */
    @Getter
    @Setter
    private String reportId;


    /**
     * 评估项ID
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = EventEvaItem.class)
    @JoinColumn(name = "ITEM_ID",referencedColumnName = "ID")
    private EventEvaItem eventEvaItem;


    /**
     * 评估成绩
     */
    @Getter
    @Setter
    private Double score;
}
