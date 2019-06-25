package com.taiji.emp.event.infoConfig.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *  接报设置--接报要求Vo对象
 *  @author qizhijie-pc
 *  @date 2018年10月22日09:48:41
 */
public class AcceptRuleVo extends IdVo<String> {

    public AcceptRuleVo(){}


    /**
     * 接报要求内容
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "接报要求内容 ruleContent长度不能超过2000")
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
    @Length(max = 2000,message = "一键事故描述 eventDesc长度不能超过2000")
    private String eventDesc;

    /**
     * 处置事故要点
     */
    @Getter@Setter
    @Length(max = 2000,message = "处置事故要点 mainPoints长度不能超过2000")
    private String mainPoints;
}
