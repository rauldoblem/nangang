package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanTeam;
import com.taiji.emp.res.vo.PlanTeamVo;
import org.mapstruct.Mapper;

/**
 * 预案团队管理基础mapper PlanTeamMapper

 */
@Mapper(componentModel = "spring")
public interface PlanTeamMapper extends BaseMapper<PlanTeam, PlanTeamVo>{
}
