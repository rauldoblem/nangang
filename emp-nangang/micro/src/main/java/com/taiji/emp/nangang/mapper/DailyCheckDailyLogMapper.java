package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.DailyCheckDailyLog;
import com.taiji.emp.nangang.vo.DailyCheckDailyLogVo;
import org.mapstruct.Mapper;

/**
 * @author yhcookie
 * @date 2018/12/3 11:38
 */
@Mapper(componentModel = "spring")
public interface DailyCheckDailyLogMapper {

    /**
     * vo To entity
     * @param vo
     * @return
     */
    default DailyCheckDailyLog voToEntity(DailyCheckDailyLogVo vo) {
        DailyCheckDailyLog entity = new DailyCheckDailyLog();
        entity.setCheckItemId(vo.getCheckItemId());
        entity.setDailylogId(vo.getDailyLog().getId());
        return entity;
    }

    default DailyCheckDailyLogVo entityToVo(DailyCheckDailyLog entity){
        DailyCheckDailyLogVo vo = new DailyCheckDailyLogVo();
        vo.setCheckItemId(entity.getCheckItemId());
        return  vo;
    };
}
