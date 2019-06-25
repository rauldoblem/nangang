package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.Position;
import com.taiji.emp.res.vo.PositionVo;
import org.mapstruct.Mapper;

/**
 * 仓位 mapper PositionMapper
 */
@Mapper(componentModel = "spring")
public interface PositionMapper extends BaseMapper<Position, PositionVo>{
}
