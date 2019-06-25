package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.DailyCheck;
import com.taiji.emp.nangang.vo.DailyCheckVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyCheckMapper extends BaseMapper<DailyCheck,DailyCheckVo> {

    @Override
    default DailyCheckVo entityToVo(DailyCheck entity) {
        DailyCheckVo vo = new DailyCheckVo();
        vo.setId(entity.getId());
        vo.setCheckDate(entity.getCheckDate());
        vo.setShiftPatternName(entity.getShiftPatternName());
        vo.setIsShift(entity.getIsShift());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());

        return vo;
    }

    @Override
    default DailyCheck voToEntity(DailyCheckVo vo) {
        DailyCheck entity = new DailyCheck();

        entity.setId(vo.getId());
        entity.setCheckDate(vo.getCheckDate());
        entity.setShiftPatternName(vo.getShiftPatternName());
        entity.setShiftPatternId(vo.getShiftPatternId());
        entity.setIsShift(vo.getIsShift());

        return entity;
    }

    @Override
    default RestPageImpl<DailyCheckVo> entityPageToVoPage(Page<DailyCheck> entityPage, Pageable page){
        if ( entityPage == null || page == null) {
            return null;
        }

        List<DailyCheck> content = entityPage.getContent();

        List<DailyCheckVo> list = new ArrayList<>(content.size());

        for ( DailyCheck entity : content ) {
            list.add(entityToVo(entity) );
        }

        RestPageImpl<DailyCheckVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }

}
