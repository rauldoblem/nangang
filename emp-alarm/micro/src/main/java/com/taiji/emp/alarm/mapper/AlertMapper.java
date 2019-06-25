package com.taiji.emp.alarm.mapper;

import com.taiji.emp.alarm.entity.Alert;
import com.taiji.emp.alarm.vo.AlertVo;
import org.mapstruct.Mapper;

/**
 * 预警信息 mapper AlertMapper
 * @author qizhijie-pc
 * @date 2018年12月13日14:35:56
 */
@Mapper(componentModel = "spring")
public interface AlertMapper extends BaseMapper<Alert,AlertVo>{
}
