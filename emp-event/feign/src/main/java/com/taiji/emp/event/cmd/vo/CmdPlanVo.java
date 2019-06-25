package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 应急处置-- 关联预案vo
 * @author qizhijie-pc
 * @date 2018年11月2日11:12:31
 */
public class CmdPlanVo extends BaseVo<String>{

    public CmdPlanVo(){}

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
    @NotEmpty(message = "planId 不能为空字符串")
    private String planId;

    //预案名称
    @Getter
    @Setter
    @Length(max =200,message = "planName 最大长度不能超过200")
    @NotEmpty(message = "planName 不能为空字符串")
    private String planName;


}
