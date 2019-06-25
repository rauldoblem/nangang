package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanOrgRespon;
import com.taiji.emp.res.vo.PlanOrgResponVo;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 预案责任人、单位管理 mapper PlanOrgResponMapper

 */
@Mapper(componentModel = "spring")
public interface PlanOrgResponMapper extends BaseMapper<PlanOrgRespon, PlanOrgResponVo>{

    @Override
    default List<PlanOrgResponVo> entityListToVoList(List<PlanOrgRespon> entityList) {
        if ( entityList == null) {
            return null;
        }
        List<PlanOrgResponVo> list = new ArrayList<>(entityList.size());
        for ( PlanOrgRespon entity : entityList ) {
            list.add( entityToVo(entity) );
        }
        return list;
    }

    @Override
    default PlanOrgResponVo entityToVo(PlanOrgRespon entity){
        if ( entity == null) {
            return null;
        }
        PlanOrgResponVo vo = new PlanOrgResponVo();
        vo.setId(entity.getId());
        vo.setPlanOrgId(entity.getPlanOrgId());
        vo.setPlanOrgName(entity.getPlanOrgName());
        vo.setSubjectType(entity.getSubjectType());
        vo.setDuty(entity.getDuty());
        vo.setResponsibility(entity.getResponsibility());
        return vo;
    }
}
