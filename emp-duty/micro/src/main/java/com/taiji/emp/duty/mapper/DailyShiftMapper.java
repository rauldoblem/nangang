package com.taiji.emp.duty.mapper;

import com.taiji.emp.duty.entity.dailyShift.DailyLogShift;
import com.taiji.emp.duty.entity.dailyShift.DailyShift;
import com.taiji.emp.duty.vo.dailyShift.DailyLogShiftVo;
import com.taiji.emp.duty.vo.dailyShift.DailyShiftVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 交接班管理 mapper DailyShiftMapper
 * @author sun yi
 * @date 2018年10月29日
 */
@Mapper(componentModel = "spring")
public interface DailyShiftMapper extends BaseMapper<DailyShift,DailyShiftVo>{

    @Override
    default RestPageImpl<DailyShiftVo> entityPageToVoPage(Page<DailyShift> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<DailyShift> content = entityPage.getContent();
        List<DailyShiftVo> list = new ArrayList<>(content.size());
        for (DailyShift entity : content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }
    default DailyShiftVo entityToVoForList(DailyShift entity){
        if (entity == null){
            return null;
        }
        List<DailyLogShift> dailyLogShiftList = entity.getDailyLogShift();
        List<DailyLogShiftVo> listVo = new ArrayList<>();
        if(null != dailyLogShiftList && dailyLogShiftList.size() > 0) {
            for (DailyLogShift dailyLogShift : dailyLogShiftList) {
                DailyLogShiftVo vo = new DailyLogShiftVo();
                vo.setId(dailyLogShift.getId());
                vo.setDailyShiftId(dailyLogShift.getDailyShiftId());
                vo.setDailyLogId(dailyLogShift.getDailyLogId());
                listVo.add(vo);
            }
        }

        DailyShiftVo vo = new DailyShiftVo();
        vo.setId(entity.getId());
        vo.setTitle(entity.getTitle());
        vo.setFromWatcherId(entity.getFromWatcherId());
        vo.setFromWatcherName(entity.getFromWatcherName());
        vo.setToWatcherId(entity.getToWatcherId());
        vo.setToWatcherName(entity.getToWatcherName());
        vo.setNotes(entity.getNotes());
        vo.setCreateOrgId(entity.getCreateOrgId());
        vo.setCreateOrgName(entity.getCreateOrgName());
        vo.setCreateBy(entity.getCreateBy());
        vo.setCreateTime(entity.getCreateTime());
        vo.setDailyLogShift(listVo);
        return vo;
    }

}
