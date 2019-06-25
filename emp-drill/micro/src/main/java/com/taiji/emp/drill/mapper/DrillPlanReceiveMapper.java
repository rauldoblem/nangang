package com.taiji.emp.drill.mapper;

import com.taiji.emp.drill.entity.DrillPlan;
import com.taiji.emp.drill.entity.DrillPlanReceive;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 演练计划转发接收 DrillPlanReceiveMapper
 * @author qzp-pc
 * @date 2018年11月01日11:21:18
 */
@Mapper(componentModel = "spring")
public interface DrillPlanReceiveMapper extends BaseMapper<DrillPlanReceive,DrillPlanReceiveVo> {

    @Override
    default RestPageImpl<DrillPlanReceiveVo> entityPageToVoPage(Page<DrillPlanReceive> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<DrillPlanReceive> content = entityPage.getContent();
        List<DrillPlanReceiveVo> list = new ArrayList<>(content.size());
        for (DrillPlanReceive entity : content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    default DrillPlanReceiveVo entityToVoForList(DrillPlanReceive entity){
        if (entity == null){
            return null;
        }

        DrillPlanReceiveVo vo = new DrillPlanReceiveVo();
        DrillPlanVo drillPlanVo = new DrillPlanVo();
        DrillPlan drillPlan = entity.getDrillPlan();
        drillPlanVo.setId(drillPlan.getId());
        drillPlanVo.setDrillName(drillPlan.getDrillName());
        drillPlanVo.setDrillWayId(drillPlan.getDrillWayId());
        drillPlanVo.setDrillWayName(drillPlan.getDrillWayName());
        drillPlanVo.setDrillTime(drillPlan.getDrillTime());
        drillPlanVo.setDrillPlace(drillPlan.getDrillPlace());
        drillPlanVo.setPartOrgIds(drillPlan.getPartOrgIds());
        drillPlanVo.setPartOrgNames(drillPlan.getPartOrgNames());
        drillPlanVo.setContent(drillPlan.getContent());
        drillPlanVo.setEpPlanIds(drillPlan.getEpPlanIds());
        drillPlanVo.setEpPlanNames(drillPlan.getEpPlanNames());
        drillPlanVo.setNotes(drillPlan.getNotes());
        drillPlanVo.setSendStatus(drillPlan.getSendStatus());
        drillPlanVo.setReportStatus(drillPlan.getReportStatus());
        drillPlanVo.setOrgId(drillPlan.getOrgId());
        drillPlanVo.setOrgName(drillPlan.getOrgName());
        vo.setDrillPlan(drillPlanVo);
        vo.setSendTime(entity.getSendTime());
        vo.setReciever(entity.getReciever());
        vo.setRecieveTime(entity.getRecieveTime());
        vo.setRecieveStatus(entity.getRecieveStatus());

        return vo;
    }

    @Override
    default List<DrillPlanReceiveVo> entityListToVoList(List<DrillPlanReceive> list) {
        if ( list == null) {
            return null;
        }

        List<DrillPlanReceiveVo> voList = new ArrayList<>(list.size());
        for (DrillPlanReceive entity : list){
            DrillPlanReceiveVo vo = new DrillPlanReceiveVo();
            vo.setId(entity.getId());
            vo.setDrillPlanId(entity.getDrillPlan().getId());
            vo.setSendType(entity.getSendType());
            vo.setSendTime(entity.getSendTime());
            vo.setOrgId(entity.getOrgId());
            vo.setOrgName(entity.getOrgName());
            vo.setReciever(entity.getReciever());
            vo.setRecieveTime(entity.getRecieveTime());
            vo.setRecieveStatus(entity.getRecieveStatus());
            voList.add(vo);
        }
        return voList;
    }
}
