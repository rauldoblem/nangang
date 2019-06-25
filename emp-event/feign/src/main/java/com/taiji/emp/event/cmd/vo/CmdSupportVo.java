package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

/**
 * 应急处置-- 关联社会依托资源vo
 * @author qizhijie-pc
 * @date 2018年11月8日10:59:15
 */
public class CmdSupportVo extends BaseVo<String> {

    public CmdSupportVo(){}

    //方案Id
    @Getter
    @Setter
    @Length(max =36,message = "schemeId 最大长度不能超过36")
    @NotEmpty(message = "schemeId 不能为空字符串")
    private String schemeId;

    //预案ID
    @Getter
    @Setter
    @Length(max =36,message = "planId 最大长度不能超过36")
    private String planId;

    //社会依托资源ID
    @Getter
    @Setter
    @Length(max =36,message = "supportId 最大长度不能超过36")
    @NotEmpty(message = "supportId 不能为空字符串")
    private String supportId;

    //社会依托资源名称
    @Getter
    @Setter
    @Length(max =100,message = "supportName 最大长度不能超过100")
    private String supportName;

    //社会依托资源类型名称
    @Getter
    @Setter
    @Length(max =100,message = "suppTypeName 最大长度不能超过100")
    private String suppTypeName;

    //社会依托资源地址
    @Getter
    @Setter
    @Length(max =100,message = "address 最大长度不能超过100")
    private String address;

    //社会依托资源地址
    @Getter
    @Setter
    @Range
//    @Length(max =6,message = "supportSize 最大长度不能超过6")
    private Double supportSize;

    //负责人
    @Getter
    @Setter
    @Length(max =50,message = "principal 最大长度不能超过50")
    private String principal;

    //负责人联系电话
    @Getter
    @Setter
    @Length(max =50,message = "principalTel 最大长度不能超过50")
    private String principalTel;

}
