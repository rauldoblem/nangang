package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.Company;
import com.taiji.emp.nangang.vo.CompanyVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CompanyMapper extends BaseMapper<Company,CompanyVo>{
}
