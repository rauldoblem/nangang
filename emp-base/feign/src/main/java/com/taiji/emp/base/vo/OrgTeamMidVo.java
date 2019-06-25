package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/28 9:36
 */
@Getter
@Setter
@NoArgsConstructor
public class OrgTeamMidVo {

    @Length(max = 36,message = "id字段最大长度36")
    public String id;

    @Length(max = 36,message = "分组id字段最大长度36")
    private String teamId;

    @Length(max = 100,message = "分组名字字段最大长度100")
    private String teamName;

    @Length(max = 36,message = "该分组内的组织机构Id字段最大长度36")
    private String orgId;

    @Length(max = 100,message = "该分组内的组织机构名字字段最大长度100")
    private String orgName;
}
