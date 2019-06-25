package com.taiji.emp.event.infoConfig.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 接报设置--接报要求实体类 AcceptInform
 * @author qizhijie-pc
 * @date 2018年10月22日14:27:10
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "IM_ACCEPT_RULE")
public class AcceptRule extends IdEntity<String> {

    public AcceptRule(){}

    /**
     * 接报要求内容
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "接报要求内容 ruleContent长度不能超过4000")
    private String ruleContent;

    /**
     * 事件类型ID
     */
    @Getter@Setter
    @NotEmpty(message = "事件类型ID不能为空")
    @Length(max = 36,message = "事件类型ID eventTypeId长度不能超过36")
    private String eventTypeId;

    /**
     * 事件类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "事件类型名称 eventTypeName长度不能超过100")
    private String eventTypeName;

    /**
     * 一键事故描述
     */
    @Getter@Setter
    @Length(max = 4000,message = "一键事故描述 eventDesc长度不能超过4000")
    private String eventDesc;

    /**
     * 处置事故要点
     */
    @Getter@Setter
    @Length(max = 4000,message = "处置事故要点 mainPoints长度不能超过4000")
    private String mainPoints;
}
