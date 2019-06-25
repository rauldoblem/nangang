package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.SmsRecieve;
import com.taiji.emp.base.vo.SmsRecieveVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SmsRecieveMapper extends BaseMapper<SmsRecieve, SmsRecieveVo>{
}
