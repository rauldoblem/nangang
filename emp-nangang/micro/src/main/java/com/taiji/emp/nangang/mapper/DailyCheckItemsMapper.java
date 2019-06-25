package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.DailyCheckItems;
import com.taiji.emp.nangang.vo.DailyCheckItemsVo;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyCheckItemsMapper extends  BaseMapper<DailyCheckItems,DailyCheckItemsVo>{

    @Override
    default List<DailyCheckItemsVo> entityListToVoList(List<DailyCheckItems> entityList) {

        List<DailyCheckItemsVo> voList = new ArrayList<>();
        for (int i = 0; i < entityList.size(); i++) {
            String id = entityList.get(i).getId();
            String dailycheckId = entityList.get(i).getDailycheckId();
            String checkItemContent = entityList.get(i).getCheckItemContent();
            String checkResult = entityList.get(i).getCheckResult();
            String checkItemId =  entityList.get(i).getCheckItemId();
            LocalDateTime updateTime =  entityList.get(i).getUpdateTime();
            DailyCheckItemsVo vo = new DailyCheckItemsVo();
            vo.setId(id);
            vo.setCheckItemId(checkItemId);
            vo.setDailycheckId(dailycheckId);
            vo.setCheckResult(checkResult);
            vo.setCheckItemContent(checkItemContent);
            vo.setUpdateTime(updateTime);
            voList.add(vo);
        }

        return voList;
    }

    @Override
    default List<DailyCheckItems> voListToEntityList(List<DailyCheckItemsVo> voList){

        List<DailyCheckItems> entityList = new ArrayList<>();
        for (int i = 0; i < voList.size(); i++) {
            String id = voList.get(i).getId();
            String dailycheckId = voList.get(i).getDailycheckId();
            String checkItemContent = voList.get(i).getCheckItemContent();
            String checkItemId = voList.get(i).getCheckItemId();
            String checkResult = voList.get(i).getCheckResult();

            DailyCheckItems entity = new DailyCheckItems();
            entity.setId(id);
            entity.setCheckItemId(checkItemId);
            entity.setDailycheckId(dailycheckId);
            entity.setCheckResult(checkResult);
            entity.setCheckItemContent(checkItemContent);
            entityList.add(entity);
        }

        return entityList;
    }
}
