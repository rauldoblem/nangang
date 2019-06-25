package com.taiji.emp.event.cmd.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应急处置--研判信息实体类 Analyse
 * @author qizhijie-pc
 * @date 2018年10月22日14:27:10
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_EVENT_ANALYSE")
public class Analyse extends BaseEntity<String> implements DelFlag{

    public Analyse(){}

    /**
     * 事件ID
     */
    @Getter
    @Setter
    @Length(max =36,message = "eventId 最大长度不能超过36")
    @NotEmpty(message = "eventId 不能为空字符串")
    private String eventId;

    /**
     * 研判意见
     */
    @Getter@Setter
    @Length(max =4000,message = "analyseResult 最大长度不能超过4000")
    @NotEmpty(message = "analyseResult 不能为空字符串")
    private String analyseResult;

    /**
     * 注意事项
     */
    @Getter@Setter
    @Length(max =4000,message = "note 最大长度不能超过4000")
    private String note;

    /**
     * 删除标志
     */
    @Getter@Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
