package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 应急处置-- 关联应急专家vo
 * @author qizhijie-pc
 * @date 2018年11月7日11:26:20
 */
public class CmdExpertVo extends BaseVo<String> {

    public CmdExpertVo(){}

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

    //专家Id
    @Getter
    @Setter
    @Length(max =36,message = "expertId 最大长度不能超过36")
    @NotEmpty(message = "expertId 不能为空字符串")
    private String expertId;

    //专家姓名
    @Getter
    @Setter
    @Length(max =50,message = "expertName 最大长度不能超过50")
    private String expertName;

    //处理事故类型名称
    @Getter
    @Setter
    @Length(max =4000,message = "eventTypeNames 最大长度不能超过4000")
    private String eventTypeNames;

    //专业特长
    @Getter
    @Setter
    @Length(max =500,message = "specialty 最大长度不能超过500")
    private String specialty;

    //专业特长
    @Getter
    @Setter
    @Length(max =50,message = "unit 最大长度不能超过50")
    private String unit;

    //联系方式
    @Getter
    @Setter
    @Length(max =16,message = "telephone 最大长度不能超过16")
    private String telephone;

}
