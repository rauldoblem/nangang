package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.PersonTypePattern;
import com.taiji.emp.duty.vo.dailylog.PersonTypePatternVo;
import org.mapstruct.Mapper;

/**
 * 值班人员设置表 PersonTypePatternMapper
 */
@Mapper(componentModel = "spring")
public interface PersonTypePatternMapper extends BaseMapper<PersonTypePattern, PersonTypePatternVo> {

}
