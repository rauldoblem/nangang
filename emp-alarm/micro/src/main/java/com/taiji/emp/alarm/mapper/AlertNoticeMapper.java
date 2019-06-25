package com.taiji.emp.alarm.mapper;

import com.taiji.emp.alarm.entity.AlertNotice;
import com.taiji.emp.alarm.vo.AlertNoticeVo;
import org.mapstruct.Mapper;

/**
 * 预警通知信息 mapper AlertNoticeMapper
 * @author qizhijie-pc
 * @date 2018年12月13日14:35:56
 */
@Mapper(componentModel = "spring")
public interface AlertNoticeMapper extends BaseMapper<AlertNotice,AlertNoticeVo>{
}
