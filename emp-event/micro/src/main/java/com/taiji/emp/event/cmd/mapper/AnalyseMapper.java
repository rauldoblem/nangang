package com.taiji.emp.event.cmd.mapper;

import com.taiji.emp.event.cmd.entity.Analyse;
import com.taiji.emp.event.cmd.vo.AnalyseVo;
import org.mapstruct.Mapper;

/**
 * 应急处置--事件研判信息类mapper AnalyseMapper
 * @author qizhijie-pc
 * @date 2018年10月30日17:57:14
 */
@Mapper(componentModel = "spring")
public interface AnalyseMapper extends BaseMapper<Analyse,AnalyseVo>{
}
