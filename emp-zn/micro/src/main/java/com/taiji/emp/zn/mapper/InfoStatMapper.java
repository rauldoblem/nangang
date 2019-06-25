package com.taiji.emp.zn.mapper;

import com.taiji.emp.zn.entity.InfoStat;
import com.taiji.emp.zn.vo.InfoStatVo;
import org.mapstruct.Mapper;

/**
 * 首页--事件信息 mapper InfoStatMapper
 * @author qizhijie-pc
 * @date 2018年12月20日18:47:49
 */
@Mapper(componentModel = "spring")
public interface InfoStatMapper extends BaseMapper<InfoStat,InfoStatVo>{
}
