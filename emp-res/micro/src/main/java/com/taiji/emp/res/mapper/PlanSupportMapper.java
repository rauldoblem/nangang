package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanSupport;
import com.taiji.emp.res.vo.PlanSupportVo;
import org.mapstruct.Mapper;

/**
 * 预案社会依托资源管理基础mapper PlanSupportMapper

 */
@Mapper(componentModel = "spring")
public interface PlanSupportMapper extends BaseMapper<PlanSupport, PlanSupportVo>{
}
