package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.CmdExpert;
import com.taiji.emp.event.cmd.entity.CmdTeam;
import com.taiji.emp.event.cmd.vo.CmdExpertVo;
import com.taiji.emp.event.cmd.vo.CmdTeamVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--关联应急队伍mapper CmdTeamMapper
 * @author qizhijie-pc
 * @date 2018年11月7日11:50:43
 */
@Mapper(componentModel = "spring")
public interface CmdTeamMapper extends BaseMapper<CmdTeam,CmdTeamVo>{
}
