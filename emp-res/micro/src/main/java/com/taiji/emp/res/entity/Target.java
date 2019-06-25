package com.taiji.emp.res.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 防护目标实体类 RcTarget
 * @author qzp-pc
 * @date 2018年10月16日12:49:08
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "RC_TARGET")
public class Target  extends BaseEntity<String> implements DelFlag {

    public Target() {}

    /**
     * 防护目标名称
     */
    @Getter
    @Setter
    @Length(max = 200,message = "防护目标名称name最大长度200")
    private String name;

    /**
     * 所属单位名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "所属单位名称unit最大长度100")
    private String unit;

    /**
     * 目标类型ID
     */
    @Getter
    @Setter
    @NotBlank(message = "目标类型ID不能为空字符串")
    @Length(max = 36,message = "目标类型ID targetTypeId字段最大长度36")
    private String targetTypeId;

    /**
     * 目标类型名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "目标类型名称 targetTypeName字段最大长度100")
    private String targetTypeName;

    /**
     * 地址
     */
    @Getter
    @Setter
    @Length(max = 200,message = "地址address最大长度200")
    private String address;

    /**
     * 经纬度
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度lonAndLat最大长度64")
    private String lonAndLat;

    /**
     * 负责人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "负责人principal最大长度50")
    private String principal;

    /**
     * 负责人电话
     */
    @Getter
    @Setter
    @Length(max = 20,message = "负责人电话principalTel最大长度20")
    private String principalTel;

    /**
     * 描述
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "描述describe最大长度4000")
    private String describes;

    /**
     * 可能的灾害形式
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "可能的灾害形式disaster最大长度2000")
    private String disaster;

    /**
     * 防控措施
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "防控措施measure最大长度2000")
    private String measure;

    /**
     * 创建单位编码
     */
    @Getter
    @Setter
    private String createOrgId;

    /**
     * 创建单位名称
     */
    @Getter
    @Setter
    private String createOrgName;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
