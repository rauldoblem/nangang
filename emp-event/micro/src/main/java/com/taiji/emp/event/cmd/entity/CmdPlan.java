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
 * 应急处置--关联预案实体类 Scheme
 * @author qizhijie-pc
 * @date 2018年11月1日17:26:37
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_PLAN")
public class CmdPlan extends BaseEntity<String> implements DelFlag {

    public CmdPlan(){}

    //方案Id
    @Getter
    @Setter
    @Length(max =36,message = "schemeId 最大长度不能超过36")
    @NotEmpty(message = "schemeId 不能为空字符串")
    private String schemeId;

    //预案ID
    @Getter
    @Setter
    @Length(max =36,message = "planId 最大长度不能超过36")
    @NotEmpty(message = "planId 不能为空字符串")
    private String planId;

    //预案名称
    @Getter
    @Setter
    @Length(max =200,message = "planName 最大长度不能超过200")
    @NotEmpty(message = "planName 不能为空字符串")
    private String planName;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
