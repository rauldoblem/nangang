package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.Recvfax;
import com.taiji.emp.base.vo.RecvfaxVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecvfaxMapper extends BaseMapper<Recvfax,RecvfaxVo> {
}
