package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanMaterial;
import com.taiji.emp.res.vo.PlanMaterialVo;
import org.mapstruct.Mapper;

/**
 * 预案物资管理基础mapper PlanMaterialMapper

 */
@Mapper(componentModel = "spring")
public interface PlanMaterialMapper extends BaseMapper<PlanMaterial, PlanMaterialVo>{
}
