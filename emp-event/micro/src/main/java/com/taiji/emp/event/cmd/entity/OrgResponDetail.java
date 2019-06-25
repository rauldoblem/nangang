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
 * 应急处置-- 应急机构责任单位/人详细信息
 * @date 2018年11月5日14:44:26
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_ORGRESPON_DETAIL")
public class OrgResponDetail extends BaseEntity<String> implements DelFlag {

    public OrgResponDetail(){}

    //应急组织责任ID
    @Getter
    @Setter
    @NotEmpty(message = "orgResponId 不能为空")
    @Length(max =36,message = "orgResponId 最大长度不能超过36")
    @Column(name = "ORGRESPON_ID")
    private String orgResponId;

    //负责部门ID
    @Getter
    @Setter
    @Length(max =36,message = "rspOrgId 最大长度不能超过36")
    @Column(name = "RSP_ORG_ID")
    private String rspOrgId;

    //负责部门名称
    @Getter
    @Setter
    @Length(max =100,message = "repOrgName 最大长度不能超过100")
    @Column(name = "RSP_ORG_NAME")
    private String repOrgName;

    //负责人
    @Getter
    @Setter
    @Length(max =50,message = "principal 最大长度不能超过50")
    private String principal;

    //负责人电话
    @Getter
    @Setter
    @Length(max =16,message = "principalTel 最大长度不能超过16")
    private String principalTel;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 预案关联责任单位/人ID
     */
    @Getter
    @Setter
    @Column(name = "PLAN_RESDETAIL_ID")
    @Length(max =36,message = "planResDetailId 最大长度不能超过16")
    private String planResDetailId;
}
