package com.taiji.emp.res.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * 预案责任人、单位详情实体类 PlanOrgResponDetail
 *
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_ORGRESPON_DETAIL")
public class PlanOrgResponDetail extends BaseEntity<String> implements DelFlag{

    public PlanOrgResponDetail(){}

    /**
     * 应急组织责任ID
     */
    @Getter@Setter
    @Length(max = 36,message = "应急组织责任ID orgresponId字段最大长度36")
    @Column(name = "ORGRESPON_ID")
    private String orgResponId;

    /**
     *  负责部门ID
     */
    @Getter@Setter
    @Length(max = 36,message = "负责部门ID rspOrgId字段最大长度36")
    @Column(name = "RSP_ORG_ID")
    private String rspOrgId;

    /**
     *  负责部门名称
     */
    @Getter@Setter
    @Length(max = 100,message = "负责部门名称rspOrgName字段最大长度100")
    @Column(name = "RSP_ORG_NAME")
    private String repOrgName;

    /**
     *  负责人
     */
    @Getter@Setter
    @Length(max = 50,message = "负责人principal字段最大长度50")
    private String principal;

    /**
     *  负责人电话
     */
    @Getter@Setter
    @Length(max = 16,message = "负责人电话principalTel字段最大长度16")
    @Column(name = "PRINCIPAL_TEL")
    private String principalTel;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
