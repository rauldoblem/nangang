package com.taiji.emp.res.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 预案责任人、单位实体类 PlanOrgRespon
 *
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_ORGRESPON")
public class PlanOrgRespon extends BaseEntity<String> implements DelFlag{

    public PlanOrgRespon(){}

    /**
     * 应急组织
     */
    @Getter@Setter
    @Length(max = 36,message = "应急组织ID planOrgId字段最大长度36")
    @Column(name = "PLAN_ORG_ID")
    private String planOrgId;

    /**
     * 应急组织名称
     */
    @Getter@Setter
    @Length(max = 100,message = "应急组织名称planOrgName字段最大长度100")
    @Column(name = "PLAN_ORG_NAME")
    private String planOrgName;

    /**
     * 责任主体类型:0个人，1单位
     */
    @Getter@Setter
    @Length(max = 1,message = "责任主体类型subjectType字段最大长度1")
    @Column(name = "SUBJECT_TYPE")
    private String subjectType;

    /**
     * 应急职务
     */
    @Getter@Setter
    @Length(max = 200,message = "应急职务duty字段最大长度200")
    private String duty;

    /**
     *  职责
     */
    @Getter@Setter
    @Length(max = 2000,message = "职责responsibility字段最大长度2000")
    private String responsibility;

    /**
     * 预案责任人、单位详情
     */
    /*@Getter
    @Setter
    @NonNull
    @OneToMany(targetEntity = PlanOrgResponDetail.class)
    @JoinColumn(name = "ORGRESPON_ID")
    private List<PlanOrgResponDetail> details;*/


    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
