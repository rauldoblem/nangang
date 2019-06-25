package com.taiji.emp.nangang.mapper;

import com.taiji.emp.nangang.entity.ConventionalFactor;
import com.taiji.emp.nangang.vo.ConventionalFactorVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ConventionalFactorMapper extends BaseMapper<ConventionalFactor,ConventionalFactorVo> {

    @Override
    default RestPageImpl<ConventionalFactorVo> entityPageToVoPage(Page<ConventionalFactor> entityPage, Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }

        List<ConventionalFactor> content = entityPage.getContent();

        List<ConventionalFactorVo> list = new ArrayList<>(content.size());

        for ( ConventionalFactor entity : content ) {
            list.add( entityToVo(entity) );
        }

        RestPageImpl<ConventionalFactorVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }

    @Override
    default ConventionalFactorVo entityToVo(ConventionalFactor entity){
        ConventionalFactorVo conventionalFactorVo = new ConventionalFactorVo();
        conventionalFactorVo.setCo(entity.getCo());
        conventionalFactorVo.setNo2(entity.getNo2());
        conventionalFactorVo.setO3(entity.getO3());
        conventionalFactorVo.setPm2_5(entity.getPm2_5());
        conventionalFactorVo.setPm10(entity.getPm10());
        conventionalFactorVo.setSo2(entity.getSo2());
        LocalDateTime createTime = entity.getCreateTime();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        if(null == createTime){
            conventionalFactorVo.setCreateTime(null);
        }else{
            String time = df.format(createTime);
            conventionalFactorVo.setCreateTime(time);
        }
        return conventionalFactorVo;
    }
}
