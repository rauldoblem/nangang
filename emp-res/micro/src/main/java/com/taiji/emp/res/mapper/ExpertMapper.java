package com.taiji.emp.res.mapper;

import com.taiji.emp.res.entity.Expert;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.emp.res.entity.Expert;
import org.mapstruct.Mapper;

/**
 * 应急专家mapper ExpertMapper
 * @author qizhijie-pc
 * @date 2018年10月10日14:32:46
 */
@Mapper(componentModel = "spring")
public interface ExpertMapper extends BaseMapper<Expert,ExpertVo>{
}
