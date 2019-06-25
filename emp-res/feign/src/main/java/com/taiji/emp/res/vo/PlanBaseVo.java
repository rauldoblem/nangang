package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 预案管理基础
 */
public class PlanBaseVo extends BaseVo<String>{

    public PlanBaseVo(){}

    /**
     * 预案名称
     */
    @Getter@Setter
    @Length(max = 100,message = "预案名称name字段最大长度100")
    private String name;

    /**
     * 发布单位
     */
    @Getter@Setter
    @Length(max = 100,message = "发布单位code字段最大长度100")
    private String unit;

    /**
     * 预案类型ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案类型ID planTypeId字段最大长度36")
    private String planTypeId;

    /**
     * 预案类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "预案类型名称planTypeName字段最大长度100")
    private String planTypeName;

    /**
     * 事件类型ID
     */
    @Getter@Setter
    @Length(max = 36,message = "事件类型ID eventTypeId字段最大长度36")
    private String eventTypeId;

    /**
     * 事件类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "事件类型名称eventTypeName字段最大长度100")
    private String eventTypeName;

    /**
     * 预案状态ID
     */
    @Getter@Setter
    @Length(max = 36,message = "预案状态ID planStatusId字段最大长度36")
    private String planStatusId;

    /**
     * 预案状态名称
     */
    @Getter@Setter
    @Length(max = 100,message = "预案状态名称planStatusName字段最大长度100")
    private String planStatusName;

    /**
     * 编制时间
     */
    @Getter@Setter
    private String compileTime;

    /**
     * 预案描述
     */
    @Getter@Setter
    @Length(max = 2000,message = "预案描述planDescri字段最大长度2000")
    private String planDescri;

    /**
     * 备注
     */
    @Getter@Setter
    @Length(max = 2000,message = "备注notes字段最大长度2000")
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

    /**
     * 预案树的ID(PlanCalTree)
     */
    @Getter@Setter
    private String planCaltreeId;
}
