package com.taiji.emp.event.eva.mapper;

import com.taiji.emp.event.eva.entity.EventEvaReport;
import com.taiji.emp.event.eva.vo.EventEvaReportDataVo;
import com.taiji.emp.event.eva.vo.EventEvaReportVo;
import com.taiji.micro.common.utils.DateUtil;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

/**
 * 事件评估报告 EventEvaReportMapper
 * @author qzp-pc
 * @date 2018年11月06日16:28:18
 */
@Mapper(componentModel = "spring")
public interface EventEvaReportMapper extends BaseMapper<EventEvaReport,EventEvaReportVo> {

    @Override
    default EventEvaReport voToEntity(EventEvaReportVo reportVo){
        if (null == reportVo){
            return null;
        }
        EventEvaReportDataVo vo = reportVo.getEventEvaReport();
        EventEvaReport entity = new EventEvaReport();
        if (!StringUtils.isEmpty(vo.getId())){
            entity.setId(vo.getId());
        }
        entity.setEventId(vo.getEventId());
        entity.setName(vo.getName());
        entity.setOverAllEva(vo.getOverallEva());
        entity.setEvaLuator(vo.getEvaluator());
        entity.setEvaLuateUnit(vo.getEvaluateUnit());
        entity.setEvaLuateTime(DateUtil.strToLocalDate(vo.getEvaluateTime()));
        entity.setTotalScore(vo.getTotalScore());
        entity.setResultGrade(vo.getResultGrade());
        entity.setAdvice(vo.getAdvice());
        entity.setNotes(vo.getNotes());
        entity.setCreateBy(vo.getCreateBy());
        entity.setUpdateBy(vo.getUpdateBy());
        return entity;
    }

    @Override
    default EventEvaReportVo entityToVo(EventEvaReport entity){
        if (null == entity){
            return null;
        }
        EventEvaReportVo reportVo = new EventEvaReportVo();
        EventEvaReportDataVo vo = new EventEvaReportDataVo();
        vo.setId(entity.getId());
        vo.setEventId(entity.getEventId());
        vo.setName(entity.getName());
        vo.setOverallEva(entity.getOverAllEva());
        vo.setEvaluator(entity.getEvaLuator());
        vo.setEvaluateUnit(entity.getEvaLuateUnit());
        vo.setEvaluateTime(DateUtil.getDateStr(entity.getEvaLuateTime()));
        vo.setTotalScore(entity.getTotalScore());
        vo.setResultGrade(entity.getResultGrade());
        vo.setAdvice(entity.getAdvice());
        vo.setNotes(entity.getNotes());
        reportVo.setFileDeleteIds(null);
        reportVo.setFileIds(null);
        reportVo.setEventEvaReport(vo);
        return reportVo;
    }
}
