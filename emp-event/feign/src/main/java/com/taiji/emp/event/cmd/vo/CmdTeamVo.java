package com.taiji.emp.event.cmd.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 应急处置-- 关联应急队伍vo
 * @author qizhijie-pc
 * @date 2018年11月7日11:26:20
 */
public class CmdTeamVo extends BaseVo<String> {

    public CmdTeamVo(){}

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

    //队伍ID
    @Getter
    @Setter
    @Length(max =36,message = "teamId 最大长度不能超过36")
    @NotEmpty(message = "teamId 不能为空字符串")
    private String teamId;

    //队伍名称
    @Getter
    @Setter
    @Length(max =100,message = "teamName 最大长度不能超过100")
    private String teamName;

    //队伍类型名称
    @Getter
    @Setter
    @Length(max =100,message = "teamTypeName 最大长度不能超过100")
    private String teamTypeName;

    //队伍常驻地址
    @Getter
    @Setter
    @Length(max =100,message = "teamAddress 最大长度不能超过100")
    private String teamAddress;

    //负责人
    @Getter
    @Setter
    @Length(max =50,message = "principal 最大长度不能超过50")
    private String principal;

    //负责人手机
    @Getter
    @Setter
    @Length(max =50,message = "principalTel 最大长度不能超过50")
    private String principalTel;

}
