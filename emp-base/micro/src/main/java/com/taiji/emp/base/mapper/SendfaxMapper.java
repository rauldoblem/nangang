package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.Sendfax;
import com.taiji.emp.base.vo.SendfaxVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SendfaxMapper extends BaseMapper<Sendfax,SendfaxVo>{
}
