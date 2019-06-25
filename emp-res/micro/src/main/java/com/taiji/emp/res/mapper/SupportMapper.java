package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.Support;
import com.taiji.emp.res.vo.SupportVo;
import org.mapstruct.Mapper;

/**
 * 应急社会依托资源mapper SupportMapper
 */
@Mapper(componentModel = "spring")
public interface SupportMapper extends BaseMapper<Support, SupportVo>{
}
