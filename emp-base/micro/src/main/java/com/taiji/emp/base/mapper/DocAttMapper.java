package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.DocAttachment;
import com.taiji.emp.base.vo.DocAttVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocAttMapper extends BaseMapper<DocAttachment,DocAttVo>{
}
