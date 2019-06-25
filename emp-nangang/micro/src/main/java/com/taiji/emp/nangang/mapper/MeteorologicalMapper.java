package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.Meteorological;
import com.taiji.emp.nangang.vo.MeteorologicalVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MeteorologicalMapper extends BaseMapper<Meteorological,MeteorologicalVo>{

    @Override
    default RestPageImpl<MeteorologicalVo> entityPageToVoPage(Page<Meteorological> entityPage, Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }

        List<Meteorological> content = entityPage.getContent();

        List<MeteorologicalVo> list = new ArrayList<>(content.size());

        for ( Meteorological entity : content ) {
            list.add( entityToVo(entity) );
        }

        RestPageImpl<MeteorologicalVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }

    @Override
    default MeteorologicalVo entityToVo(Meteorological entity){
        MeteorologicalVo meteorologicalVo = new MeteorologicalVo();
        if(null == meteorologicalVo){
            return null;
        }
        meteorologicalVo.setTemperature(entity.getTemperature());
        meteorologicalVo.setHumidity(entity.getHumidity());
        meteorologicalVo.setWindSpeed(entity.getWindSpeed());
        meteorologicalVo.setAtmosphericPressure(entity.getAtmosphericPressure());
        meteorologicalVo.setWindDirection(entity.getWindDirection());
        LocalDateTime createTime = entity.getCreateTime();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        if(null == createTime){
            meteorologicalVo.setCreateTime(null);
        }else{
            String time = df.format(createTime);
            meteorologicalVo.setCreateTime(time);
        }
        return meteorologicalVo;
    }
}
