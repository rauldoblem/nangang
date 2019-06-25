package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.OrgTeam;
import com.taiji.emp.base.vo.OrgTeamVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/12/28 10:55
 */
@Mapper(componentModel = "spring")
public interface OrgTeamMapper extends BaseMapper<OrgTeam,OrgTeamVo>{
}
