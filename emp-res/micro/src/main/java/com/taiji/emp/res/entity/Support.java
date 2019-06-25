package com.taiji.emp.res.entity;


import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 应急社会依托资源表实体类 Support
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ER_SUPPORT")
public class Support extends BaseEntity<String> implements DelFlag{

    public Support(){}

    /**
     * 依托资源名称
     */
    @Getter@Setter
    @NotEmpty(message = "依托资源名称不能为空")
    @Length(max = 200,message = "依托资源名称name字段最大长度200")
    private String name;

    /**
     * 依托资源类型
     */
    @Getter@Setter
    @Length(max = 36,message = "依托资源类型suppTypeId字段最大长度36")
    @Column(name = "SUPP_TYPE_ID")
    private String typeId;

    /**
     * 依托资源类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "依托资源类型名称suppTypeName字段最大长度100")
    @Column(name = "SUPP_TYPE_NAME")
    private String typeName;

    /**
     * 依托资源地址
     */
    @Getter@Setter
    @Length(max = 200,message = "依托资源地址address字段最大长度200")
    private String address;

    /**
     * 经纬度
     */
    @Getter
    @Setter
    @Column(name = "LON_AND_LAT")
    @Length(max = 64,message = "经纬度 lonAndLat字段最大长度64")
    private String lonAndLat;

    /**
     * 负责人
     */
    @Getter
    @Setter
//    @Column(name = "PRINCIPAL_NAME")
    @Length(max = 50,message = "负责人 principalName字段最大长度50")
    private String principal;

    /**
     * 负责人电话
     */
    @Getter
    @Setter
    @Column(name = "PRINCIPAL_TEL")
    @Length(max = 50,message = "负责人联系方式 principalTel字段最大长度50")
    private String principalTel;

    /**
     * 场所大小（单位：平方米）
     */
    @Getter@Setter
    @Range(min=0)
    @Column(name = "SUPPORT_SIZE")
    private Double supportSize;

    /**
     * 可容纳人数
     */
    @Getter@Setter
    @Min(value=0,message = "可容纳人数最小为0")
    @Max(value=9999,message = "可容纳人数最大为9999")
    private Integer capacity;

    /**
     * 资源描述
     */
    @Getter@Setter
    @Length(max = 4000,message = "资源描述describe字段最大长度4000")
    private String describes;

    /**
     * 描述信息
     */
    @Getter@Setter
    @Length(max = 1000,message = "描述信息notes字段最大长度1000")
    private String notes;

    /**
     * 创建单位ID
     */
    @Getter@Setter
    private String createOrgId;

    /**
     * 创建单位编码
     */
    @Getter@Setter
    private String createOrgName;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
