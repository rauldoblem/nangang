package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.OrgTeamMid;
import com.taiji.emp.base.vo.OrgTeamMidVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/12/28 14:44
 */
@Mapper(componentModel = "spring")
public interface OrgTeamMidMapper extends BaseMapper<OrgTeamMid,OrgTeamMidVo> {
}
