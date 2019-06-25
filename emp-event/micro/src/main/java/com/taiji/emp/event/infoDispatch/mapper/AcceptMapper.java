package com.taiji.emp.event.infoDispatch.mapper;

import com.taiji.emp.event.infoDispatch.entity.Accept;
import com.taiji.emp.event.infoDispatch.vo.AcceptVo;
import org.mapstruct.Mapper;

/**
 * 报送信息类mapper AcceptMapper
 * @author qizhijie-pc
 * @date 2018年10月24日10:30:47
 */
@Mapper(componentModel = "spring")
public interface AcceptMapper extends BaseMapper<Accept,AcceptVo>{
}
