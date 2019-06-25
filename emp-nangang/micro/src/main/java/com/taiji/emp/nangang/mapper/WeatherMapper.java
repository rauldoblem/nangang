package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.Weather;
import com.taiji.emp.nangang.vo.WeatherVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/11/19 15:59
 */
@Mapper(componentModel = "spring")
public interface WeatherMapper extends  BaseMapper<Weather,WeatherVo>{

}

