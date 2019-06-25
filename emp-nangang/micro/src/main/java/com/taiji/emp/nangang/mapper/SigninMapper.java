package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.Signin;
import com.taiji.emp.nangang.vo.SigninVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SigninMapper extends BaseMapper<Signin,SigninVo>{
}
