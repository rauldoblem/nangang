package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.dailylog.DailyLog;
import com.taiji.emp.duty.entity.dailylog.DailyLogTreatment;
import com.taiji.emp.duty.vo.dailylog.DailyLogTreatmentVo;
import com.taiji.emp.duty.vo.dailylog.DailyLogVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 值班日志 DailyLogMapper
 * @author qzp-pc
 * @date 2018年10月28日18:24:20
 */
@Mapper(componentModel = "spring")
public interface DailyLogMapper extends BaseMapper<DailyLog,DailyLogVo> {

    @Override
    default DailyLogVo entityToVo(DailyLog entity) {

        if ( entity == null) {
            return null;
        }
        DailyLogVo vo = new DailyLogVo();
        vo.setId(entity.getId());
        vo.setAffirtTypeId(entity.getAffirtTypeId());
        vo.setAffirtTypeName(entity.getAffirtTypeName());
        vo.setEmeGradeFlag(entity.getEmeGradeFlag());
        vo.setTreatTime(entity.getTreatTime());
        vo.setTreatStatus(entity.getTreatStatus());
        vo.setInputerId(entity.getInputerId());
        vo.setInputerName(entity.getInputerName());
        vo.setLogContent(entity.getLogContent());
        return vo;
    }

    @Override
    default RestPageImpl<DailyLogVo> entityPageToVoPage(Page<DailyLog> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<DailyLog> content = entityPage.getContent();
        List<DailyLogVo> list = new ArrayList<>(content.size());
        for (DailyLog entity : content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    @Override
    default List<DailyLogVo> entityListToVoList(List<DailyLog> list) {
        if ( list == null) {
            return null;
        }
        List<DailyLogVo> listVo = new ArrayList<>(list.size());
        for (DailyLog entity : list){
            listVo.add(entityToVoForList(entity));
        }
        return listVo;
    }

    default DailyLogVo entityToVoForList(DailyLog entity){
        if (entity == null){
            return null;
        }
        List<DailyLogTreatment> dLogTreatment = entity.getDLogTreatment();
        List<DailyLogTreatmentVo> listVo = new ArrayList<>();
        if(null != dLogTreatment && dLogTreatment.size() > 0) {
            for (DailyLogTreatment treatment : dLogTreatment) {
                DailyLogTreatmentVo vo = new DailyLogTreatmentVo();
                vo.setId(treatment.getId());
                vo.setDLogId(treatment.getDailyLog().getId());
                vo.setDlogTreatment(treatment.getDlogTreatment());
                vo.setTreatBy(treatment.getTreatBy());
                vo.setTreatTime(treatment.getTreatTime());
                vo.setTreatStatus(treatment.getTreatStatus());
                listVo.add(vo);
            }
        }

        DailyLogVo vo = new DailyLogVo();
        vo.setId(entity.getId());
        vo.setAffirtTypeId(entity.getAffirtTypeId());
        vo.setAffirtTypeName(entity.getAffirtTypeName());
        vo.setEmeGradeFlag(entity.getEmeGradeFlag());
        vo.setTreatTime(entity.getTreatTime());
        vo.setTreatStatus(entity.getTreatStatus());
        vo.setInputerId(entity.getInputerId());
        vo.setInputerName(entity.getInputerName());
        vo.setLogContent(entity.getLogContent());
        vo.setDLogTreatment(listVo);
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }
}
