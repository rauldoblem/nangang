package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanOrgResponDetail;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import org.mapstruct.Mapper;

/**
 * 预案责任人、单位管理详情 mapper PlanOrgResponDetailMapper

 */
@Mapper(componentModel = "spring")
public interface PlanOrgResponDetailMapper extends BaseMapper<PlanOrgResponDetail, PlanOrgResponDetailVo>{
}
