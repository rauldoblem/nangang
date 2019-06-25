package com.taiji.emp.event.eva.mapper;

import com.taiji.emp.event.eva.entity.EventEvaItem;
import com.taiji.emp.event.eva.vo.EventEvaItemVo;
import org.mapstruct.Mapper;

/**
 * 事件评估项目 EventEvaItemMapper
 * @author qzp-pc
 * @date 2018年11月06日16:28:18
 */
@Mapper(componentModel = "spring")
public interface EventEvaItemMapper extends BaseMapper<EventEvaItem,EventEvaItemVo> {

}
