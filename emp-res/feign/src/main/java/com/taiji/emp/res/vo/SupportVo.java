package com.taiji.emp.res.vo;


import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * 应急社会依托资源表 feign supportVo
 */
public class SupportVo extends BaseVo<String>{

    public SupportVo(){}

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
    @Length(max = 36,message = "依托资源类型typeId字段最大长度36")
    private String typeId;

    /**
     * 依托资源类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "依托资源类型名称typeName字段最大长度100")
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
    @Length(max = 64,message = "经纬度 lonAndLat字段最大长度64")
    private String lonAndLat;

    /**
     * 负责人
     */
    @Getter
    @Setter
    @Length(max = 50,message = "负责人 principal字段最大长度50")
    private String principal;

    /**
     * 负责人电话
     */
    @Getter
    @Setter
    @Length(max = 50,message = "负责人联系方式 principalTel字段最大长度50")
    private String principalTel;

    @Getter@Setter
    @Range(min=0)
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
    @Length(max = 50,message = "创建单位ID createOrgId字段最大长度50")
    private String createOrgId;

    /**
     * 创建单位编码
     */
    @Getter@Setter
    @Length(max = 100,message = "创建单位编码 createOrgName字段最大长度100")
    private String createOrgName;

}
