package com.taiji.emp.event.redisEva.mapper;

import com.taiji.emp.event.redis.vo.ProcessRedisNodeVo;
import com.taiji.emp.event.redisEva.entity.ProcessRedisNode;
import org.mapstruct.Mapper;

/**
 * 过程再现流程 mapper EventEvaProcessMapper
 * @author sun yi
 * @date 2018年11月20日
 */
@Mapper(componentModel = "spring")
public interface ProcessNodeRedisMapper extends BaseMapper<ProcessRedisNode, ProcessRedisNodeVo> {
}
