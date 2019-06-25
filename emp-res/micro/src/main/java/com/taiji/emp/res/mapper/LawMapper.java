package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.Law;
import com.taiji.emp.res.vo.LawVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LawMapper extends BaseMapper<Law,LawVo>{

}
