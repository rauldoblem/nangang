package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.Telrecord;
import com.taiji.emp.base.vo.TelrecordVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelrecordMapper extends BaseMapper<Telrecord, TelrecordVo>{
}
