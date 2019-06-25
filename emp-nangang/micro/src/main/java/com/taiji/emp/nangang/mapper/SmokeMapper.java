package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.Smoke;
import com.taiji.emp.nangang.vo.SmokeVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/12/10 11:45
 */
@Mapper(componentModel = "spring")
public interface SmokeMapper extends BaseMapper<Smoke,SmokeVo>{
}
