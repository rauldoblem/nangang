package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.Team;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.emp.res.entity.Team;
import org.mapstruct.Mapper;

/**
 * 救援队伍mapper TeamMapper
 * @author qizhijie-pc
 * @date 2018年10月15日14:38:57
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends BaseMapper<Team,TeamVo>{
}
