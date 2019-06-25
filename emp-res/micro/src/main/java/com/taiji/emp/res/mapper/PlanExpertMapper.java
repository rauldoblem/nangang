package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanExpert;
import com.taiji.emp.res.vo.PlanExpertVo;
import org.mapstruct.Mapper;

/**
 * 预案专家管理基础mapper PlanExpertMapper

 */
@Mapper(componentModel = "spring")
public interface PlanExpertMapper extends BaseMapper<PlanExpert, PlanExpertVo>{
}
