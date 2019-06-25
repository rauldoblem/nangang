package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.CmdSupport;
import com.taiji.emp.event.cmd.vo.CmdSupportVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--关联社会依托资源mapper CmdSupportMapper
 * @author qizhijie-pc
 * @date 2018年11月8日10:56:01
 */
@Mapper(componentModel = "spring")
public interface CmdSupportMapper extends BaseMapper<CmdSupport,CmdSupportVo>{
}
