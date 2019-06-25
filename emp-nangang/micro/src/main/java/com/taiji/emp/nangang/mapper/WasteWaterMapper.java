package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.WasteWater;
import com.taiji.emp.nangang.vo.WasteWaterVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/12/10 11:42
 */
@Mapper(componentModel = "spring")
public interface WasteWaterMapper extends BaseMapper<WasteWater,WasteWaterVo>{
}
