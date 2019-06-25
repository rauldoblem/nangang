package com.taiji.emp.zn.mapper;

import com.taiji.emp.zn.entity.PushAlert;
import com.taiji.emp.zn.vo.PushAlertVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/12/22 20:55
 */
@Mapper(componentModel = "spring")
public interface ZNAlertMapper extends BaseMapper<PushAlert,PushAlertVo>{
}
