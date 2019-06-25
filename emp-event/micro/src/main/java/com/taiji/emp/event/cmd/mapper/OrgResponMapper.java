package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.OrgRespon;
import com.taiji.emp.event.cmd.vo.OrgResponVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--应急机构责任单位/人mapper OrgResponMapper
 * @author qizhijie-pc
 * @date 2018年11月2日15:49:36
 */
@Mapper(componentModel = "spring")
public interface OrgResponMapper extends BaseMapper<OrgRespon,OrgResponVo>{
}
