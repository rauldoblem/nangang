package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.Material;
import com.taiji.emp.res.vo.MaterialVo;
import org.mapstruct.Mapper;

/**
 * 应急物资mapper MaterialMapper

 */
@Mapper(componentModel = "spring")
public interface MaterialMapper extends BaseMapper<Material, MaterialVo>{
}
