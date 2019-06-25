package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.dailylog.DailyLog;
import com.taiji.emp.duty.entity.dailylog.DailyLogTreatment;
import com.taiji.emp.duty.vo.dailylog.DailyLogTreatmentVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 值班日志 DailyLogTreatmentMapper
 * @author qzp-pc
 * @date 2018年10月29日14:54:20
 */
@Mapper(componentModel = "spring")
public interface DailyLogTreatmentMapper extends BaseMapper<DailyLogTreatment,DailyLogTreatmentVo> {

//    @Override
    default DailyLogTreatment voToEntity(DailyLogTreatmentVo vo) {
        DailyLogTreatment entity = new DailyLogTreatment();
        DailyLog dailyLog = new DailyLog();
        dailyLog.setId(vo.getDLogId());
        entity.setDailyLog(dailyLog);
        entity.setTreatBy(vo.getTreatBy());
        entity.setDlogTreatment(vo.getDlogTreatment());
        entity.setTreatTime(vo.getTreatTime());
        entity.setTreatStatus(vo.getTreatStatus());
        return entity;
    }


    @Override
    default DailyLogTreatmentVo entityToVo(DailyLogTreatment entity) {
        DailyLogTreatmentVo vo = new DailyLogTreatmentVo();
        DailyLogVo dailyLogVo = new DailyLogVo();
        dailyLogVo.setId(null);
        vo.setDailyLog(dailyLogVo);
        setEntities(vo,entity);
        vo.setId(entity.getId());
        return vo;
    }

    @Override
    default List<DailyLogTreatmentVo> entityListToVoList(List<DailyLogTreatment> list) {
        if (list == null){
            return null;
        }
        List<DailyLogTreatmentVo> voList = new ArrayList<>(list.size());
        for (DailyLogTreatment entity : list){
            DailyLogTreatmentVo vo = new DailyLogTreatmentVo();
            vo.setId(entity.getId());
            setEntities(vo,entity);
            voList.add(vo);
        }
        return voList;
    }

    default void setEntities(DailyLogTreatmentVo vo,DailyLogTreatment entity){
        vo.setDLogId(entity.getDailyLog().getId());
        vo.setDlogTreatment(entity.getDlogTreatment());
        vo.setTreatBy(entity.getTreatBy());
        vo.setTreatTime(entity.getTreatTime());
        vo.setTreatStatus(entity.getTreatStatus());
    }
}
