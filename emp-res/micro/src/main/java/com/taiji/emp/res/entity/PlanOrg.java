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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 应急预案组织机构管理表实体类 PlanOrg
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EP_PLAN_ORG")
public class PlanOrg extends BaseEntity<String> implements DelFlag{

    public PlanOrg(){}

    /**
     * 预案ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案ID planId字段最大长度36")
    @Column(name = "PLAN_ID")
    private String planId;

    /**
     * 响应级别ID（事件等级ID）
     */
    @Getter@Setter
    @Length(max = 36,message = "响应级别ID（事件等级ID）eventGradeID字段最大长度36")
    @Column(name = "EVENT_GRADE_ID")
    private String eventGradeId;

    /**
     * 响应级别名称（事件等级名称）
     */
    @Getter@Setter
    @Length(max = 100,message = "响应级别名称（事件等级名称）eventGradeName字段最大长度100")
    @Column(name = "EVENT_GRADE_NAME")
    private String eventGradeName;

    /**
     * 应急组织名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "应急组织名称name字段最大长度100")
    private String name;

    /**
     * 父节点
     */
    @Getter
    @Setter
    @Column(name = "PARENT_ID")
    @Length(max = 36,message = "父节点parentId字段最大长度36")
    private String parentId;

    /**
     * 排序号
     */
    @Getter
    @Setter
    @Min(value=0,message = "初始数量最小为0")
    @Max(value=9999,message = "初始数量最大为9999")
    private Integer orders;

    /**
     * 是否叶子节点
     */
    @Getter@Setter
    @Length(max = 1,message = "是否叶子节点leaf字段最大长度1")
    private String leaf;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 是否主责：0否，1是
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否主责isMain字段最大长度1")
    private String isMain;

}
