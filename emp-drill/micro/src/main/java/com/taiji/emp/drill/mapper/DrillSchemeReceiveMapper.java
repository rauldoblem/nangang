package com.taiji.emp.drill.mapper;

import com.taiji.emp.drill.entity.DrillScheme;
import com.taiji.emp.drill.entity.DrillSchemeReceive;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.emp.drill.vo.DrillSchemeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 演练方案转发接收 DrillSchemeReceiveMapper
 * @author qzp-pc
 * @date 2018年11月05日11:20:18
 */
@Mapper(componentModel = "spring")
public interface DrillSchemeReceiveMapper extends BaseMapper<DrillSchemeReceive,DrillSchemeReceiveVo> {

    @Override
    default RestPageImpl<DrillSchemeReceiveVo> entityPageToVoPage(Page<DrillSchemeReceive> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<DrillSchemeReceive> content = entityPage.getContent();
        List<DrillSchemeReceiveVo> list = new ArrayList<>(content.size());
        for (DrillSchemeReceive entity : content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    default DrillSchemeReceiveVo entityToVoForList(DrillSchemeReceive entity){
        if (entity == null){
            return null;
        }
        DrillSchemeReceiveVo vo = new DrillSchemeReceiveVo();
        DrillSchemeVo schemeVo = new DrillSchemeVo();
        DrillScheme drillScheme = entity.getDrillScheme();
        schemeVo.setId(drillScheme.getId());
        schemeVo.setDrillPlanId(drillScheme.getDrillPlanId());
        schemeVo.setDrillPlanName(drillScheme.getDrillPlanName());
        schemeVo.setDrillName(drillScheme.getDrillName());
        schemeVo.setSummary(drillScheme.getSummary());
        schemeVo.setEpPlanIds(drillScheme.getEpPlanIds());
        schemeVo.setEpPlanNames(drillScheme.getEpPlanNames());
        schemeVo.setSendStatus(drillScheme.getSendStatus());
        schemeVo.setReportStatus(drillScheme.getReportStatus());
        schemeVo.setOrgId(drillScheme.getOrgId());
        schemeVo.setOrgName(drillScheme.getOrgName());
        schemeVo.setCreateBy(drillScheme.getCreateBy());
        schemeVo.setCreateTime(drillScheme.getCreateTime());
        vo.setDrillScheme(schemeVo);
        vo.setSendTime(entity.getSendTime());
        vo.setReciever(entity.getReciever());
        vo.setRecieveTime(entity.getRecieveTime());
        vo.setRecieveStatus(entity.getRecieveStatus());
        return vo;
    }

    @Override
    default List<DrillSchemeReceiveVo> entityListToVoList(List<DrillSchemeReceive> list) {
        List<DrillSchemeReceiveVo> voList = new ArrayList<>();
        for (DrillSchemeReceive entity : list){
            DrillSchemeReceiveVo vo = new DrillSchemeReceiveVo();
            vo.setId(entity.getId());
            vo.setDrillSchemeId(entity.getDrillScheme().getId());
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
