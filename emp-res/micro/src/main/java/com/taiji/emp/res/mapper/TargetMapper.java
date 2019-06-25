package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.Target;
import com.taiji.emp.res.vo.TargetVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 防护目标 TargetMapper
 * @author qzp-pc
 * @date 2018年10月16日13:38:20
 */
@Mapper(componentModel = "spring")
public interface TargetMapper extends BaseMapper<Target,TargetVo> {

    @Override
    default RestPageImpl<TargetVo> entityPageToVoPage(Page<Target> entityPage, Pageable page) {
        if ( entityPage == null || page == null) {
            return null;
        }
        List<Target> content = entityPage.getContent();
        List<TargetVo> list = new ArrayList<>(content.size());
        for (Target entity : content){
            list.add(entityToVoForList(entity));
        }
        RestPageImpl voPage = new RestPageImpl(list, page, entityPage.getTotalElements());
        return voPage;
    }

    default TargetVo entityToVoForList(Target entity){
        if ( entity == null ) {
            return null;
        }
        TargetVo rcTargetVo = new TargetVo();
        rcTargetVo.setId(entity.getId());
        rcTargetVo.setCreateTime(entity.getCreateTime());
        rcTargetVo.setUpdateTime(entity.getUpdateTime());
        rcTargetVo.setCreateBy(entity.getCreateBy());
        rcTargetVo.setUpdateBy(entity.getUpdateBy());
        rcTargetVo.setName(entity.getName());
        rcTargetVo.setUnit(entity.getUnit());
        rcTargetVo.setTargetTypeId(entity.getTargetTypeId());
        rcTargetVo.setTargetTypeName(entity.getTargetTypeName());
        rcTargetVo.setAddress(entity.getAddress());
        rcTargetVo.setLonAndLat(entity.getLonAndLat());
        rcTargetVo.setPrincipal(entity.getPrincipal());
        rcTargetVo.setPrincipalTel(entity.getPrincipalTel());
        rcTargetVo.setDescribes(entity.getDescribes());
        rcTargetVo.setDisaster(entity.getDisaster());
        rcTargetVo.setMeasure(entity.getMeasure());
        rcTargetVo.setCreateOrgId(entity.getCreateOrgId());
        rcTargetVo.setCreateOrgName(entity.getCreateOrgName());

        return rcTargetVo;
    }
}
