package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.ShiftPattern;
import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import org.mapstruct.Mapper;

/**
 * 班次设置表 ShiftPatternMapper
 */
@Mapper(componentModel = "spring")
public interface ShiftPatternMapper extends BaseMapper<ShiftPattern, ShiftPatternVo> {

}
