package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.CompanyInfo;
import com.taiji.emp.nangang.vo.CompanyInfoVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/12/20 11:25
 */
@Mapper(componentModel = "spring")
public interface CompanyInfoMapper extends BaseMapper<CompanyInfo,CompanyInfoVo>{
}
