package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 危险源管理 feign HazardVo
 * @author SunYi
 * @date 2018年10月16日
 */
public class HazardVo extends BaseVo<String>{

    public HazardVo(){}

    /**
     * 姓名
     */
    @Getter
    @Setter
    @NotBlank(message = "危险源名称 name 不能为空字符串")
    @Length(max = 100,message = "姓名name字段最大长度100")
    private String name;

    /**
     * 单位
     */
    @Getter
    @Setter
    @Length(max = 36,message = "单位 unit字段最大长度36")
    private String unit;

    /**
     * 类型ID
     */
    @Getter
    @Setter
    @Length(max = 100,message = "类型ID danTypeId 字段最大长度100")
    private String danTypeId;

    /**
     * 类型名
     */
    @Getter
    @Setter
    @Length(max = 36,message = "类型名 danTypeName 字段最大长度100")
    private String danTypeName;

    /**
     * 级别ID
     */
    @Getter
    @Setter
    @Length(max = 50,message = "级别ID danGradeId 字段最大长度50")
    private String danGradeId;

    /**
     * 级别名
     */
    @Getter
    @Setter
    @Length(max = 100,message = "级别名 danGradeName 字段最大长度100")
    private String danGradeName;

    /**
     * 经纬度
     */
    @Getter
    @Setter
    @Length(max = 64,message = "经纬度 lonAndLat 字段最大长度64")
    private String lonAndLat;

    /**
     * 地址
     */
    @Getter
    @Setter
    @Length(max = 200,message = "危险源地址 address 字段最大长度200")
    private String address;

    /**
     * 标准临界值
     */
    @Getter
    @Setter
    //@Length(max = 6,message = "标准临界值 criticalValue 字段最大长度6")
    private int criticalValue;

    /**
     * 最大危险区面积
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
