package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.DutyTeam;
import com.taiji.emp.duty.vo.DutyTeamVo;
import org.mapstruct.Mapper;

/**
 * 值班人员分组表 DutyTeamMapper
 */
@Mapper(componentModel = "spring")
public interface DutyTeamMapper extends BaseMapper<DutyTeam, DutyTeamVo> {

}
