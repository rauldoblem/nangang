package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 预案组织机构管理
 */
public class PlanOrgResponDetailVo extends BaseVo<String>{

    public PlanOrgResponDetailVo(){}

    /**
     *  应急组织责任ID
     */
    @Getter@Setter
    @Length(max = 36,message = "应急组织责任ID orgResponId字段最大长度36")
    private String orgResponId;

    /**
     *  负责部门ID
     */
    @Getter@Setter
    @Length(max = 36,message = "负责部门ID rspOrgId字段最大长度36")
    private String rspOrgId;

    /**
     *  负责部门名称
     */
    @Getter@Setter
    @Length(max = 100,message = "负责部门名称rspOrgName字段最大长度100")
    private String repOrgName;

    /**
     *  负责人
     */
    @Getter@Setter
    @Length(max = 50,message = "负责人principal字段最大长度50")
    private String principal;

    /**
     *  负责人电话
     */
    @Getter@Setter
    @Length(max = 16,message = "负责人电话principalTel字段最大长度16")
    private String principalTel;

}
