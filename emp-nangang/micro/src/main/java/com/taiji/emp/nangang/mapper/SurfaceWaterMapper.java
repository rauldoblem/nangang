package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.SurfaceWater;
import com.taiji.emp.nangang.vo.SurfaceWaterVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SurfaceWaterMapper extends BaseMapper<SurfaceWater,SurfaceWaterVo> {
}
