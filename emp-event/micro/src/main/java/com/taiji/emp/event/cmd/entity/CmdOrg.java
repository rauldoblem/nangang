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
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 应急处置--关联应急组织机构实体类 CmdOrg
 * @author qizhijie-pc
 * @date 2018年11月5日17:10:13
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "EC_EMG_ORG")
public class CmdOrg extends BaseEntity<String> implements DelFlag{

    public CmdOrg(){}

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
    private String planId;

    //应急组织名称
    @Getter
    @Setter
    @Length(max =100,message = "name 最大长度不能超过100")
    private String name;

    //父节点
    @Getter
    @Setter
    @Length(max =36,message = "parentId 最大长度不能超过36")
    private String parentId;

    //排序号
    @Getter
    @Setter
    @Min(value=0,message = "初始数量最小为0")
    @Max(value=9999,message = "初始数量最大为9999")
    private Integer orders;

    //是否叶子节点
    @Getter
    @Setter
    @Length(max =1,message = "leaf 最大长度不能超过1")
    private String leaf;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    //预案关联组织ID
    @Getter
    @Setter
    @Length(max =36,message = "planOrgId 最大长度不能超过36")
    private String planOrgId;

}
