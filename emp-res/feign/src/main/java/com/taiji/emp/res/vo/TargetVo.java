package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 防护目标实体类 feign RcTargetVo
 * @author qzp-pc
 * @date 2018年10月16日13:31:03
 */
public class TargetVo extends BaseVo<String> {

    public TargetVo() {}

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
     * 创建人
     */
    @Getter
    @Setter
    private String createBy;

    /**
     * 更新人
     */
    @Getter
    @Setter
    private String updateBy;
}
