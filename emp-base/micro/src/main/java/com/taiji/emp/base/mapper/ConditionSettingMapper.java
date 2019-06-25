package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.ConditionSetting;
import com.taiji.emp.base.vo.ConditionSettingVo;
import org.mapstruct.Mapper;

import javax.swing.text.html.parser.ContentModel;

/**
 * @author yhcookie
 * @date 2018/12/29 15:32
 */
@Mapper(componentModel = "spring")
public interface ConditionSettingMapper extends BaseMapper<ConditionSetting,ConditionSettingVo> {
}
