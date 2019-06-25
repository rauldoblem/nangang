package com.taiji.emp.nangang.mapper;

import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface BaseMapper<ENTITY,VO> {
    VO entityToVo(ENTITY entity);
    ENTITY voToEntity(VO vo);

    default List<VO> entityListToVoList(List<ENTITY> entityList)
    {
        if ( entityList == null) {
            return null;
        }

        List<VO> list = new ArrayList<>(entityList.size());

        for ( ENTITY entity : entityList ) {
            list.add( entityToVo(entity) );
        }

        return list;
    }

    default List<ENTITY> voListToEntityList(List<VO> voList)
    {
        if ( voList == null) {
            return null;
        }


        List<ENTITY> list = new ArrayList<>(voList.size());

        for ( VO vo : voList ) {
            list.add( voToEntity(vo) );
        }

        return list;
    }

    default RestPageImpl<VO> entityPageToVoPage(Page<ENTITY> entityPage, Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }

        List<ENTITY> content = entityPage.getContent();

        List<VO> list = new ArrayList<VO>(content.size());

        for ( ENTITY entity : content ) {
            list.add( entityToVo(entity) );
        }

        RestPageImpl<VO> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }
}
