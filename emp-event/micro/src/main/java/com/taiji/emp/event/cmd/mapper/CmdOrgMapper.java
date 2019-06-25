package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.CmdOrg;
import com.taiji.emp.event.cmd.vo.CmdOrgVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--关联应急组织机构mapper CmdOrgMapper
 * @author qizhijie-pc
 * @date 2018年11月2日15:49:36
 */
@Mapper(componentModel = "spring")
public interface CmdOrgMapper extends BaseMapper<CmdOrg,CmdOrgVo>{
}
