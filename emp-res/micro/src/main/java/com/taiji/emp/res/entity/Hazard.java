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
 * 危险源管理实体类 Hazard
 * @author SunYi
 * @date 2018年10月16日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "RC_HAZARD")
public class Hazard extends BaseEntity<String> implements DelFlag {

    public Hazard(){}

    /**
     * 危险源名称
     */
    @Getter
    @Setter
    @NotBlank(message = "危险源名称 name 不能为空字符串")
    @Length(max = 200,message = "姓名name 字段最大长度200")
    private String name;

    /**
     * 单位
     */
    @Getter
    @Setter
    @Length(max = 100,message = "单位 unit字段最大长度100")
    private String unit;

    /**
     * 危险源类型ID
     */
    @Getter
    @Setter
    @Column(name = "DAN_TYPE_ID")
    @Length(max = 36,message = "危险源类型ID danTypeId字段最大长度36")
    private String danTypeId;

    /**
     * 危险源类型名
     */
    @Getter
    @Setter
    @Column(name = "DAN_TYPE_NAME")
    @Length(max = 100,message = "危险源类型名 danTypeName字段最大长度100")
    private String danTypeName;

    /**
     * 危险源级别ID
     */
    @Getter
    @Setter
    @Column(name = "DAN_GRADE_ID")
    @Length(max = 36,message = "危险源级别ID danGradeId 字段最大长度36")
    private String danGradeId;

    /**
     * 危险源级别名
     */
    @Getter
    @Setter
    @Column(name = "DAN_GRADE_NAME")
    @Length(max = 100,message = "危险源级别名 danGradeName 字段最大长度100")
    private String danGradeName;

    /**
     *经纬度
     */
    @Getter
    @Setter
    @Column(name = "LON_AND_LAT")
    @Length(max = 64,message = "经纬度 lonAndLat 字段最大长度64")
    private String lonAndLat;

    /**
     * 危险源地址
     */
    @Getter
    @Setter
    @Length(max = 200,message = "危险源地址 address 字段最大长度200")
    private String address;

    /**
     *标准临界值
     */
    @Getter
    @Setter
    private int criticalValue;

    /**
     *最大危险区面积
     */
    @Getter
    @Setter
    @Length(max = 50,message = "最大危险区面积 maxDanArea 字段最大长度50")
    private String maxDanArea;



    /**
     * 危险源描述
     */
    @Getter
    @Setter
    @Length(max = 4000,message = "危险源描述 describe 字段最大长度4000")
    private String describes;



    /**
     * 主要危险
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "主要危险 majorHazard 字段最大长度2000")
    private String majorHazard;


    /**
     * 可能存在的危害形式
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "可能存在的危害形式 disaster 字段最大长度2000")
    private String disaster;


    /**
     * 防控措施
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "防控措施 measure 字段最大长度2000")
    private String measure;

    /**
     * 负责人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "负责人 principal 字段最大长度50")
    private String principal;

    /**
     * 负责人联系方式
     */
    @Getter
    @Setter
    @Length(max = 20,message = "负责人联系方式 principalTel 字段最大长度20")
    private String principalTel;


    /**
     * 创建单位ID
     */
    @Getter
    @Setter
    @Length(max = 50,message = "创建单位ID createOrgId 字段最大长度50")
    private String createOrgId;


    /**
     * 创建单位名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "创建单位名称 createOrgName 字段最大长度100")
    private String createOrgName;



    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 原因
     */
    @Getter
    @Setter
    @Length(max = 200,message = "原因 reason 字段最大长度200")
    private String reason;

    /**
     * 应急处置建议
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "应急处置建议 suggest 字段最大长度2000")
    private String suggest;

    /**
     * 编号
     */
    @Getter
    @Setter
    @Length(max = 100,message = "编号serialNum 字段最大长度100")
    private String serialNum;

    /**
     * 区域
     */
    @Getter
    @Setter
    @Length(max = 100,message = "区域 area 字段最大长度100")
    private String area;

    /**
     * 风险名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "风险名称 riskName 字段最大长度100")
    private String riskName;


}
