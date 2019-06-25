package com.taiji.emp.drill.mapper;

import com.taiji.emp.drill.entity.DrillScheme;
import com.taiji.emp.drill.vo.DrillSchemeVo;
import org.mapstruct.Mapper;

/**
 * 演练方案 DrillSchemeMapper
 * @author qzp-pc
 * @date 2018年11月05日11:20:18
 */
@Mapper(componentModel = "spring")
public interface DrillSchemeMapper extends BaseMapper<DrillScheme,DrillSchemeVo> {

}
