package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanBase;
import com.taiji.emp.res.vo.PlanBaseVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 预案管理基础mapper PlanBaseMapper

 */
@Mapper(componentModel = "spring")
public interface PlanBaseMapper extends BaseMapper<PlanBase, PlanBaseVo>{

    @Override
    default List<PlanBaseVo> entityListToVoList(List<PlanBase> entityList) {
        if ( entityList == null) {
            return null;
        }
        List<PlanBaseVo> list = new ArrayList<>(entityList.size());
        for ( PlanBase entity : entityList ) {
            list.add( entityToVo(entity) );
        }
        return list;
    }

    @Override
    default RestPageImpl<PlanBaseVo> entityPageToVoPage(Page<PlanBase> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<PlanBase> content = entityPage.getContent();
        List<PlanBaseVo> list = new ArrayList<>(content.size());
        for (PlanBase entity : content){
            list.add(entityToVo(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    @Override
    default PlanBase voToEntity(PlanBaseVo vo){
        if ( vo == null) {
            return null;
        }
        PlanBase planBase = new PlanBase();
        planBase.setId(vo.getId());
        planBase.setName(vo.getName());
        planBase.setUnit(vo.getUnit());
        planBase.setPlanTypeId(vo.getPlanTypeId());
        planBase.setPlanTypeName(vo.getPlanTypeName());
        planBase.setEventTypeId(vo.getEventTypeId());
        planBase.setEventTypeName(vo.getEventTypeName());
        planBase.setPlanStatusId(vo.getPlanStatusId());
        planBase.setPlanStatusName(vo.getPlanStatusName());
        planBase.setCompileTime(DateUtil.strToLocalDate(vo.getCompileTime()));
        planBase.setPlanDescri(vo.getPlanDescri());
        planBase.setNotes(vo.getNotes());
        planBase.setCreateOrgId(vo.getCreateOrgId());
        planBase.setCreateOrgName(vo.getCreateOrgName());
        planBase.setPlanCalTreeId(vo.getPlanCaltreeId());
        return planBase;
    }

    @Override
    default PlanBaseVo entityToVo(PlanBase entity){
        if ( entity == null) {
            return null;
        }
        PlanBaseVo planBaseVo = new PlanBaseVo();
        planBaseVo.setId(entity.getId());
        planBaseVo.setName(entity.getName());
        planBaseVo.setUnit(entity.getUnit());
        planBaseVo.setPlanTypeId(entity.getPlanTypeId());
        planBaseVo.setPlanTypeName(entity.getPlanTypeName());
        planBaseVo.setEventTypeId(entity.getEventTypeId());
        planBaseVo.setEventTypeName(entity.getEventTypeName());
        planBaseVo.setPlanStatusId(entity.getPlanStatusId());
        planBaseVo.setPlanStatusName(entity.getPlanStatusName());
        planBaseVo.setCompileTime(DateUtil.getDateStr(entity.getCompileTime()));
        planBaseVo.setPlanDescri(entity.getPlanDescri());
        planBaseVo.setNotes(entity.getNotes());
        planBaseVo.setCreateOrgId(entity.getCreateOrgId());
        planBaseVo.setCreateOrgName(entity.getCreateOrgName());
        planBaseVo.setPlanCaltreeId(entity.getPlanCalTreeId());
        return planBaseVo;
    }
}
