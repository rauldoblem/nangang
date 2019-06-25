package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 应急储备库 RepertoryVo
 *
 */
public class RepertoryVo extends BaseVo<String>{

    public RepertoryVo(){}

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
    @Length(max = 100,message = "物资库名称 name字段最大长度100")
    private String name;

    /**
     * 库地址
     */
    @Getter
    @Setter
    @Length(max = 50,message = "库地址 repertoryAddress字段最大长度50")
    private String repertoryAddress;

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
    @Length(max = 50,message = "负责人 principalName字段最大长度50")
    private String principalName;

    /**
     * 负责人联系方式
     */
    @Getter
    @Setter
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
}
