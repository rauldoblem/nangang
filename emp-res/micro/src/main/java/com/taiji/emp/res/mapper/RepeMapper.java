package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.Repertory;
import com.taiji.emp.res.vo.RepertoryVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

/**
 * 应急储备库mapper RepeMapper
 *
 */
@Mapper(componentModel = "spring")
public interface RepeMapper extends BaseMapper<Repertory, RepertoryVo>{

    @Override
    default RestPageImpl<RepertoryVo> entityPageToVoPage(Page<Repertory> entityPage, Pageable page){
        if ( entityPage == null || page == null) {
            return null;
        }

        List<Repertory> name = entityPage.getContent();

        List<RepertoryVo> list = new ArrayList<RepertoryVo>(name.size());

        for ( Repertory entity : name ) {
            list.add( entityToVoForList(entity) );
        }

        RestPageImpl<RepertoryVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }

    default RepertoryVo entityToVoForList(Repertory entity){
        if ( entity == null ) {
            return null;
        }

        RepertoryVo repertoryVo = new RepertoryVo();

        repertoryVo.setId( entity.getId() );
        repertoryVo.setCreateTime( entity.getCreateTime() );
        repertoryVo.setUpdateTime( entity.getUpdateTime() );
        repertoryVo.setCreateBy( entity.getCreateBy() );
        repertoryVo.setUpdateBy( entity.getUpdateBy() );
        repertoryVo.setUnit( entity.getUnit() );
        repertoryVo.setCode( entity.getCode() );
        repertoryVo.setName( entity.getName() );
        repertoryVo.setRepertoryAddress( entity.getRepertoryAddress() );
        repertoryVo.setLonAndLat( entity.getLonAndLat() );
        repertoryVo.setPrincipalName( entity.getPrincipalName() );
        repertoryVo.setPrincipalTel( entity.getPrincipalTel() );
        repertoryVo.setCreateOrgId(entity.getCreateOrgId());
        repertoryVo.setCreateOrgName(entity.getCreateOrgName());
        return repertoryVo;
    }
}
