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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 应急储备库实体类 Repertory
 *
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ER_REPERTORY")
public class Repertory extends BaseEntity<String> implements DelFlag {

    public Repertory(){}

    /**
     * 所属单位
     */
    @Getter
    @Setter
    @Length(max = 100,message = "所属单位 unit字段最大长度100")
    private String unit;

    /**
     * 物资库编码
     */
    @Getter
    @Setter
    @Length(max = 36,message = "物资库编码 code字段最大长度36")
    private String code;

    /**
     * 物资库名称
     */
    @Getter
    @Setter
    @NotBlank(message = "物资库名称不能为空字符串")
    @Length(max = 100,message = "物资库名称 name字段最大长度100")
    private String name;

    /**
     * 库地址
     */
    @Getter
    @Setter
    @Length(max = 50,message = "库地址 repertoryAddress字段最大长度50")
    @Column(name = "ADDRESS")
    private String repertoryAddress;

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
    @Column(name = "PRINCIPAL_NAME")
    @Length(max = 50,message = "负责人 principalName字段最大长度50")
    private String principalName;

    /**
     * 负责人联系方式
     */
    @Getter
    @Setter
    @Column(name = "PRINCIPAL_TEL")
    @Length(max = 50,message = "负责人联系方式 principalTel字段最大长度50")
    private String principalTel;

    /**
     * 创建单位ID
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
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
