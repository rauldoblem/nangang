package com.taiji.emp.drill.mapper;

import com.taiji.emp.drill.entity.DrillPlan;
import com.taiji.emp.drill.vo.DrillPlanVo;
import org.mapstruct.Mapper;

/**
 * 演练计划 DrillPlanMapper
 * @author qzp-pc
 * @date 2018年11月01日11:21:18
 */
@Mapper(componentModel = "spring")
public interface DrillPlanMapper extends BaseMapper<DrillPlan,DrillPlanVo> {

}
