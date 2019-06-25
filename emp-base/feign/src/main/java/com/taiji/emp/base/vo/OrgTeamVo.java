package com.taiji.emp.base.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/28 9:35
 */
@Getter
@Setter
@NoArgsConstructor
public class OrgTeamVo extends BaseVo<String>{

    @Length(max = 36,message = "id字段最大长度36")
    public String id;

    @Length(max = 100,message = "分组名字字段最大长度100")
    private String teamName;

    @Length(max = 8,message = "排序字段最大长度8")
    private String orders;

    @Length(max = 36,message = "创建分组的用户ID字段最大长度36")
    private String createUserId;
}
