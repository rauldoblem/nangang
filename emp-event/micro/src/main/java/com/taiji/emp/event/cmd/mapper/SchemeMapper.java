package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.Scheme;
import com.taiji.emp.event.cmd.vo.SchemeVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--处置方案类mapper SchemeMapper
 * @author qizhijie-pc
 * @date 2018年11月1日17:09:49
 */
@Mapper(componentModel = "spring")
public interface SchemeMapper extends BaseMapper<Scheme,SchemeVo>{
}
