package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.Hazard;
import com.taiji.emp.res.vo.HazardVo;
import org.mapstruct.Mapper;

/**
 * 危险源管理 mapper HazardMapper
 * @author sun yi
 * @date 2018年10月16日
 */
@Mapper(componentModel = "spring")
public interface HazardMapper extends BaseMapper<Hazard,HazardVo>{
}
