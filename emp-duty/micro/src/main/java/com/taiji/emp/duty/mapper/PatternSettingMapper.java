package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.PatternSetting;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import org.mapstruct.Mapper;

/**
 * 值班模式设置表 PatternSettingMapper
 */
@Mapper(componentModel = "spring")
public interface PatternSettingMapper extends BaseMapper<PatternSetting, PatternSettingVo> {

}
