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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 接报设置--通知单位实体类 AcceptInform
 * @author qizhijie-pc
 * @date 2018年10月22日14:27:10
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "IM_ACCEPT_INFORM")
public class AcceptInform extends IdEntity<String>{

    public AcceptInform(){}

    /**
     * 通知部门名称
     */
    @Getter
    @Setter
    @Length(max = 200,message = "通知部门名称 orgName长度不能超过200")
    private String orgName;

    /**
     * 部门职责
     */
    @Getter@Setter
    @Length(max = 4000,message = "部门职责 orgDuty长度不能超过4000")
    private String orgDuty;

    /**
     * 负责人ID
     */
    @Getter@Setter
    @NotEmpty(message = "负责人ID不能为空")
    @Length(max = 36,message = "负责人ID principalId长度不能超过36")
    private String principalId;

    /**
     * 负责人姓名
     */
    @Getter@Setter
    @Length(max = 100,message = "负责人姓名 principal长度不能超过100")
    private String principal;

    /**
     * 联系方式
     */
    @Getter@Setter
    @Length(max = 50,message = "联系方式 principalTel长度不能超过50")
    private String principalTel;

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
     * 排序
     */
    @Getter
    @Setter
    @Min(value=0,message = "初始数量最小为0")
    @Max(value=9999,message = "初始数量最大为9999")
    private Integer orders;

    /**
     * 指令类型ID
     */
    @Getter@Setter
    @NotEmpty(message = "指令类型ID不能为空")
    @Length(max = 36,message = "指令类型ID orderTypeId长度不能超过36")
    private String orderTypeId;

    /**
     * 指令类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "指令类型名称 orderTypeName长度不能超过100")
    private String orderTypeName;
}
