package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.CmdMaterial;
import com.taiji.emp.event.cmd.vo.CmdMaterialVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--关联应急物资mapper CmdMaterialMapper
 * @author qizhijie-pc
 * @date 2018年11月8日10:56:01
 */
@Mapper(componentModel = "spring")
public interface CmdMaterialMapper extends BaseMapper<CmdMaterial,CmdMaterialVo>{
}
