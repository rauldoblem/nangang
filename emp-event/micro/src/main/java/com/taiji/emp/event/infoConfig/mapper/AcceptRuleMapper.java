package com.taiji.emp.event.infoConfig.mapper;

import com.taiji.emp.event.infoConfig.entity.AcceptInform;
import com.taiji.emp.event.infoConfig.entity.AcceptRule;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import com.taiji.emp.event.infoConfig.vo.AcceptRuleVo;
import org.mapstruct.Mapper;

/**
 * 接口设置-- 接报要求mapper AcceptRuleMapper
 * @author qizhijie-pc
 * @date 2018年10月22日12:08:13
 */
@Mapper(componentModel = "spring")
public interface AcceptRuleMapper extends BaseMapper<AcceptRule,AcceptRuleVo>{
}
