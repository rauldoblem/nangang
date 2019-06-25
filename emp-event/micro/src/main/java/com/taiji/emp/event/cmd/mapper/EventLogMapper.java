package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.EventLog;
import com.taiji.emp.event.cmd.vo.EventLogVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventLogMapper extends BaseMapper<EventLog,EventLogVo>{
}
