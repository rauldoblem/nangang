package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.PlantLayout;
import com.taiji.emp.base.vo.PlantLayoutVo;
import org.mapstruct.Mapper;

/**
 * 厂区平面图 PlantLayoutMapper
 * @author qzp-pc
 * @date 2019年01月17日18:11:17
 */
@Mapper(componentModel = "spring")
public interface PlantLayoutMapper extends BaseMapper<PlantLayout,PlantLayoutVo> {
}
