package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanTask;
import com.taiji.emp.res.vo.PlanTaskVo;
import org.mapstruct.Mapper;

/**
 * 预案任务设置管理mapper PlanTaskMapper

 */
@Mapper(componentModel = "spring")
public interface PlanTaskMapper extends BaseMapper<PlanTask, PlanTaskVo>{
}
