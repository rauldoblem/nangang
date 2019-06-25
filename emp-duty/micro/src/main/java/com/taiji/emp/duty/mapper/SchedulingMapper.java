package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.DutyTeam;
import com.taiji.emp.duty.entity.Person;
import com.taiji.emp.duty.entity.Scheduling;
import com.taiji.emp.duty.vo.PersonVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.micro.common.utils.DateUtil;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 值班表 SchedulingMapper
 */
@Mapper(componentModel = "spring")
public interface SchedulingMapper extends BaseMapper<Scheduling, SchedulingVo> {

    @Override
    default List<SchedulingVo> entityListToVoList(List<Scheduling> entityList) {
        if ( entityList == null) {
            return null;
        }
        List<SchedulingVo> list = new ArrayList<>(entityList.size());
        for ( Scheduling entity : entityList ) {
            list.add( entityToVo(entity) );
        }
        return list;
    }

    @Override
    default List<Scheduling> voListToEntityList(List<SchedulingVo> voList)
    {
        if ( voList == null) {
            return null;
        }


        List<Scheduling> list = new ArrayList<Scheduling>(voList.size());

        for (SchedulingVo vo : voList ) {
            list.add( voToEntity(vo) );
        }

        return list;
    }

    default Scheduling voToEntity(SchedulingVo vo){
        if ( vo == null) {
            return null;
        }
        Scheduling entity = new Scheduling();
        entity.setId(vo.getId());
        entity.setOrgId(vo.getOrgId());
        entity.setOrgName(vo.getOrgName());
        entity.setDutyDate(DateUtil.strToLocalDate(vo.getDutyDate()));
        if (!StringUtils.isEmpty(vo.getDateTypeCode())){
            entity.setDateTypeCode(String.valueOf(vo.getDateTypeCode()));
        }

        entity.setDateTypeName(vo.getDateTypeName());
        entity.setHolidayName(vo.getHolidayName());
        entity.setShiftPatternId(vo.getShiftPatternId());
        entity.setShiftPatternName(vo.getShiftPatternName());
        DutyTeam dutyTeam = new DutyTeam();
        dutyTeam.setId(vo.getDutyTeamId());
        entity.setDutyTeamId(dutyTeam);
        entity.setDutyTeamName(vo.getDutyTeamName());
        entity.setPtypeCode(vo.getPtypeCode());
        PersonVo personVo = vo.getPerson();

        Person person = new Person();
        person.setId(vo.getPersonId());
        entity.setPerson(person);

        entity.setPersonName(vo.getPersonName());
        entity.setHisPersonId(vo.getHisPersonId());
        entity.setHisPersonName(vo.getHisPersonName());
        entity.setStartTime(DateUtil.strToLocalDateTime(vo.getStartTime()));
        entity.setEndTime(DateUtil.strToLocalDateTime(vo.getEndTime()));
        entity.setNumber(vo.getNumber());
        if (!StringUtils.isEmpty(vo.getDateTypeCode())){
            vo.setDateTypeCode(Integer.valueOf(vo.getDateTypeCode()));
        }
        return entity;
    }


    default SchedulingVo entityToVo(Scheduling entity){
        if ( entity == null) {
            return null;
        }
        SchedulingVo vo = new SchedulingVo();
        vo.setId(entity.getId());
        vo.setOrgId(entity.getOrgId());
        vo.setOrgName(entity.getOrgName());
        vo.setDutyDate(DateUtil.getDateStr(entity.getDutyDate()));
        if (!StringUtils.isEmpty(entity.getDateTypeCode())){
            vo.setDateTypeCode(Integer.valueOf(entity.getDateTypeCode()));
        }

        vo.setDateTypeName(entity.getDateTypeName());
        vo.setHolidayName(entity.getHolidayName());
        vo.setShiftPatternId(entity.getShiftPatternId());
        vo.setShiftPatternName(entity.getShiftPatternName());
        DutyTeam dutyTeam = entity.getDutyTeamId();
        if (null != dutyTeam) {
            vo.setDutyTeamId(dutyTeam.getId());
            vo.setOrderTeam(dutyTeam.getOrderTeam());
        }
        vo.setDutyTeamName(entity.getDutyTeamName());
        vo.setPtypeCode(entity.getPtypeCode());
        Person person = entity.getPerson();
        if (!StringUtils.isEmpty(person)) {
            vo.setPersonId(person.getId());
//            DutyTeam dutyTeam = person.getDutyTeam();
//            if (!StringUtils.isEmpty(dutyTeam)){
//                vo.setTeamId(dutyTeam.getId());
//            }
        }
        vo.setDutyDate(DateUtil.getDateStr(entity.getDutyDate()));
        vo.setPersonName(entity.getPersonName());
        vo.setHisPersonId(entity.getHisPersonId());
        vo.setHisPersonName(entity.getHisPersonName());
        vo.setStartTime(DateUtil.getDateTimeStr(entity.getStartTime()));
        vo.setEndTime(DateUtil.getDateTimeStr(entity.getEndTime()));
        vo.setNumber(entity.getNumber());
        if (!StringUtils.isEmpty(entity.getDateTypeCode())){
            vo.setDateTypeCode(Integer.valueOf(entity.getDateTypeCode()));
        }
        return vo;
    }
}
