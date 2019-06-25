package com.taiji.emp.event.infoConfig.mapper;

import com.taiji.emp.event.infoConfig.entity.AcceptInform;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import org.mapstruct.Mapper;

/**
 * 接口设置-- 通知单位mapper AcceptInformMapper
 * @author qizhijie-pc
 * @date 2018年10月22日12:08:13
 */
@Mapper(componentModel = "spring")
public interface AcceptInformMapper extends BaseMapper<AcceptInform,AcceptInformVo>{
}
