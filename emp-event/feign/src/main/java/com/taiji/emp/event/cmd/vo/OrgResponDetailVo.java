package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 应急处置-- 应急机构责任单位/人详细信息vo
 * @date 2018年11月5日14:44:26
 */
public class OrgResponDetailVo extends BaseVo<String> {

    public OrgResponDetailVo(){}

    //应急组织责任ID
    @Getter
    @Setter
    @Length(max =36,message = "orgResponId 最大长度不能超过36")
    private String orgResponId;

    //负责部门ID
    @Getter
    @Setter
    @Length(max =36,message = "rspOrgId 最大长度不能超过36")
    private String rspOrgId;

    //负责部门名称
    @Getter
    @Setter
    @Length(max =100,message = "repOrgName 最大长度不能超过100")
    private String repOrgName;

    //负责人
    @Getter
    @Setter
    @Length(max =50,message = "principal 最大长度不能超过50")
    private String principal;

    //负责人电话
    @Getter
    @Setter
    @Length(max =16,message = "principalTel 最大长度不能超过16")
    private String principalTel;

    /**
     * 预案关联责任单位/人ID
     */
    @Getter
    @Setter
    @Length(max =36,message = "planResDetailId 最大长度不能超过16")
    private String planResDetailId;

}
