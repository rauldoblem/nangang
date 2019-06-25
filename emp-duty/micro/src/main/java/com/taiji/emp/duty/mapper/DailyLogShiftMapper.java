package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.dailyShift.DailyLogShift;
import com.taiji.emp.duty.vo.dailyShift.DailyLogShiftVo;
import org.mapstruct.Mapper;

/**
 * 交接班管理 mapper DailyLogShiftMapper
 */
@Mapper(componentModel = "spring")
public interface DailyLogShiftMapper extends BaseMapper<DailyLogShift,DailyLogShiftVo>{

}
