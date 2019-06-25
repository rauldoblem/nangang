package com.taiji.emp.base.mapper;

import com.taiji.emp.base.entity.ContactTeam;
import com.taiji.emp.base.vo.ContactTeamVo;
import org.mapstruct.Mapper;

/**
 * 通讯录组管理 mapper ContactTeamMapper
 * @author sun yi
 * @date 2018年10月22日
 */
@Mapper(componentModel = "spring")
public interface ContactTeamMapper extends BaseMapper<ContactTeam,ContactTeamVo>{
}
