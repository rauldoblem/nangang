package com.taiji.emp.event.infoDispatch.mapper;

import com.taiji.emp.event.infoDispatch.entity.Event;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import org.mapstruct.Mapper;

/**
 * 事件信息类mapper EventMapper
 * @author qizhijie-pc
 * @date 2018年10月26日10:32:52
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends BaseMapper<Event,EventVo>{
}
