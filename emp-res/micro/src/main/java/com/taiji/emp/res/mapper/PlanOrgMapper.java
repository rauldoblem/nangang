package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanOrg;
import com.taiji.emp.res.vo.PlanOrgVo;
import org.mapstruct.Mapper;

/**
 * 预案组织机构管理mapper PlanOrgMapper

 */
@Mapper(componentModel = "spring")
public interface PlanOrgMapper extends BaseMapper<PlanOrg, PlanOrgVo>{
}
