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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 应急处置-- 应急机构责任单位/人
 * @date 2018年11月5日14:42:04
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_ORGRESPON")
public class OrgRespon extends BaseEntity<String> implements DelFlag {

    public OrgRespon(){}

    //应急组织ID
    @Getter
    @Setter
    @NotEmpty(message = "emgOrgId 不能为空")
    @Length(max =36,message = "emgOrgId 最大长度不能超过36")
    private String emgOrgId;

    //应急组织名称
    @Getter
    @Setter
    @Length(max =100,message = "emgOrgName 最大长度不能超过100")
    private String emgOrgName;

    //责任主体类型:0个人，1单位
    @Getter
    @Setter
    @Length(max =1,message = "subjectType 最大长度不能超过1")
    private String subjectType;

    //应急职务
    @Getter
    @Setter
    @Length(max =200,message = "duty 最大长度不能超过200")
    private String duty;

    //职责
    @Getter
    @Setter
    @Length(max =2000,message = "responsibility 最大长度不能超过2000")
    private String responsibility;

    //应急机构责任单位/人详细信息
    @Getter
    @Setter
//    @NotNull
    @OneToMany(targetEntity = OrgResponDetail.class)
    @JoinColumn(name = "ORGRESPON_ID")
    private List<OrgResponDetail> details;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 预案关联组织责任ID
     */
    @Getter
    @Setter
    @Length(max =36,message = "planResponId 最大长度不能超过36")
    private String planResponId;

}
