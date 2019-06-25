package com.taiji.emp.event.eva.mapper;

import com.taiji.emp.event.eva.entity.EventEvaProcess;
import com.taiji.emp.event.eva.vo.EventEvaProcessVo;
import org.mapstruct.Mapper;

/**
 * 过程再现 mapper EventEvaProcessMapper
 * @author sun yi
 * @date 2018年10月30日
 */
@Mapper(componentModel = "spring")
public interface EventEvaProcessMapper extends BaseMapper<EventEvaProcess, EventEvaProcessVo> {
}
