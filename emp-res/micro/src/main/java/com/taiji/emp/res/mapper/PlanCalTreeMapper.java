package com.taiji.emp.res.mapper;


import com.taiji.emp.res.entity.PlanBase;
import com.taiji.emp.res.entity.PlanCalTree;
import com.taiji.emp.res.vo.PlanBaseVo;
import com.taiji.emp.res.vo.PlanCalTreeVo;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 预案预案目录mapper PlanCalTreeMapper

 */
@Mapper(componentModel = "spring")
public interface PlanCalTreeMapper extends BaseMapper<PlanCalTree, PlanCalTreeVo>{


    @Override
    default List<PlanCalTreeVo> entityListToVoList(List<PlanCalTree> entityList)
    {
        if ( entityList == null) {
            return null;
        }


        List<PlanCalTreeVo> list = new ArrayList<>(entityList.size());

        for ( PlanCalTree entity : entityList ) {
            list.add( entityToVoForList(entity) );
        }

        return list;
    }

    default PlanCalTreeVo entityToVoForList(PlanCalTree entity){
        if ( entity == null) {
            return null;
        }
        List<PlanBase> list = entity.getPlanBaseList();
        List<PlanBaseVo> listVo = new ArrayList<PlanBaseVo>();
        if (!CollectionUtils.isEmpty(list)){
            for (PlanBase planBase:list) {
                if (planBase.getDelFlag().equals("1")){
                    PlanBaseVo planBaseVo = new PlanBaseVo();
                    planBaseVo.setPlanCaltreeId(planBase.getPlanCalTreeId());
                    //其他字段需要再添加
                    listVo.add(planBaseVo);
                }
            }
        }


        PlanCalTreeVo planCalTreeVo = new PlanCalTreeVo();
        planCalTreeVo.setId(entity.getId());
        planCalTreeVo.setCalName(entity.getCalName());
        planCalTreeVo.setLeaf(entity.getLeaf());
        planCalTreeVo.setParentId(entity.getParentId());
        planCalTreeVo.setOrders(entity.getOrders());
       // planCalTreeVo.setPlanBaseList(listVo);
        if (CollectionUtils.isEmpty(listVo)){
            planCalTreeVo.setPlanNums(0);
        }else {
            planCalTreeVo.setPlanNums(listVo.size());
        }
        return planCalTreeVo;
    }


    @Override
    default PlanCalTreeVo entityToVo(PlanCalTree entity){
        if ( entity == null) {
            return null;
        }
        PlanCalTreeVo planCalTreeVo = new PlanCalTreeVo();
        planCalTreeVo.setId(entity.getId());
        planCalTreeVo.setCalName(entity.getCalName());
        planCalTreeVo.setLeaf(entity.getLeaf());
        planCalTreeVo.setParentId(entity.getParentId());
        planCalTreeVo.setOrders(entity.getOrders());
        return planCalTreeVo;
    }

    @Override
    default PlanCalTree voToEntity(PlanCalTreeVo vo){
        if ( vo == null) {
            return null;
        }
        PlanCalTree planCalTree = new PlanCalTree();
        planCalTree.setId(vo.getId());
        planCalTree.setCalName(vo.getCalName());
        planCalTree.setLeaf(vo.getLeaf());
        planCalTree.setParentId(vo.getParentId());
        planCalTree.setOrders(vo.getOrders());
        return planCalTree;
    }
}
