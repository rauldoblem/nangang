package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.track.TaskFeedback;
import com.taiji.emp.event.cmd.vo.trackVo.TaskFeedbackVo;
import com.taiji.emp.event.eva.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CmdTaskFeedbackMapper extends BaseMapper<TaskFeedback, TaskFeedbackVo>{
}
