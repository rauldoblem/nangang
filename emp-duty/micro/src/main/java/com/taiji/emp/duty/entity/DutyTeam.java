package com.taiji.emp.duty.entity;

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
 * 值班人员分组表 DutyTeam
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_DUTY_TEAM")
public class DutyTeam extends BaseEntity<String> implements DelFlag {

    public DutyTeam() {}

    /**
     *  分组名称
     */
    @Getter
    @Setter
    @Column(name = "TEAM_NAME")
    @Length(max = 50,message = "分组名称 teamName字段最大长度50")
    private String teamName;

    /**
     *  分组所属单位ID
     */
    @Getter
    @Setter
    @Column(name = "ORG_ID")
    @Length(max = 36,message = "分组所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     *  分组所属单位名称
     */
    @Getter
    @Setter
    @Column(name = "ORG_NAME")
    @Length(max = 50,message = "分组所属单位名称 orgName字段最大长度50")
    private String orgName;

    /**
     *  是否交接班，0否，1是
     */
    @Getter
    @Setter
    @Column(name = "IS_SHIFT")
    @Length(max = 1,message = "是否交接班 isShift字段最大长度1")
    private String isShift;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     *  排序字段
     */
    @Getter
    @Setter
    @Min(value=0,message = "排序编码最小为0")
    @Max(value=9999,message = "排序编码最大为9999")
    private Integer orderTeam;
}
