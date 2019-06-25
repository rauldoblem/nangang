package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.CalenSetting;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import org.mapstruct.Mapper;

/**
 * 日历设置表 CalenSettingMapper
 */
@Mapper(componentModel = "spring")
public interface CalenSettingMapper extends BaseMapper<CalenSetting, CalenSettingVo> {

}
