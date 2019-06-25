package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 14:01
 */
@Getter
@Setter
@NoArgsConstructor
public class OrgTeamMidSaveVo {

    @Length(max = 36 ,message = "分组id字段最长36位")
    private String teamId;

    private List<String> orgIds;
}
