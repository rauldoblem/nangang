package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.CmdExpert;
import com.taiji.emp.event.cmd.vo.CmdExpertVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--关联应急专家mapper CmdExpertMapper
 * @author qizhijie-pc
 * @date 2018年11月7日11:50:43
 */
@Mapper(componentModel = "spring")
public interface CmdExpertMapper extends BaseMapper<CmdExpert,CmdExpertVo>{
}
