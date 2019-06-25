package com.taiji.emp.alarm.mapper;

import com.taiji.emp.alarm.entity.NoticeFeedback;
import com.taiji.emp.alarm.vo.NoticeFeedbackVo;
import org.mapstruct.Mapper;

/**
 * 预警通知反馈信息 mapper NoticeFeedbackMapper
 * @author qizhijie-pc
 * @date 2018年12月13日14:35:56
 */
@Mapper(componentModel = "spring")
public interface NoticeFeedbackMapper extends BaseMapper<NoticeFeedback,NoticeFeedbackVo>{
}
