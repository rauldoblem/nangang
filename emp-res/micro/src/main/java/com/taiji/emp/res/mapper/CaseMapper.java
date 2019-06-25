package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.CaseEntity;
import com.taiji.emp.res.vo.CaseEntityVo;
import org.mapstruct.Mapper;

/**
 * 案例信息 mapper ContactMapper
 * @author sun yi
 * @date 2018年11月2日
 */
@Mapper(componentModel = "spring")
public interface CaseMapper extends BaseMapper<CaseEntity,CaseEntityVo>{
}
