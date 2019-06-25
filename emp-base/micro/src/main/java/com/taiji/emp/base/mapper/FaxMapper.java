package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.Fax;
import com.taiji.emp.base.vo.FaxVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FaxMapper extends BaseMapper<Fax ,FaxVo>{
}
