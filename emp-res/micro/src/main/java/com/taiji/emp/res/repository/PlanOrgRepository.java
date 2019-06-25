package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanOrg;
import com.taiji.emp.res.entity.QPlanOrg;
import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class PlanOrgRepository extends BaseJpaRepository<PlanOrg,String>{

    //应急预案组织机构管理list -- 不分页
    public List<PlanOrg> findList(PlanOrgListVo planOrgListVo){

        String planId = planOrgListVo.getPlanId();
        //String eventGradeId = planOrgListVo.getEventGradeId();
        List<String>planIds = planOrgListVo.getPlanIds();
        String isMain = planOrgListVo.getIsMain();
        QPlanOrg planOrg = QPlanOrg.planOrg;

        JPQLQuery<PlanOrg> query = from(planOrg);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(planId)){
            builder.and(planOrg.planId.contains(planId));
        }

//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planOrg.eventGradeId.contains(eventGradeId));
//        }

        if(null!=planIds&&planIds.size()>0){
            builder.and(planOrg.planId.in(planIds));
        }
        if(StringUtils.hasText(isMain)){
            builder.and(planOrg.isMain.eq(isMain));
        }
        builder.and(planOrg.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(PlanOrg.class
                ,planOrg.id
                ,planOrg.planId
                ,planOrg.eventGradeId
                ,planOrg.eventGradeName
                ,planOrg.name
                ,planOrg.parentId
                ,planOrg.orders
                ,planOrg.leaf
                ,planOrg.isMain
        )).where(builder)
                .orderBy(planOrg.updateTime.desc());
        return findAll(query);
    }

    @Override
    @Transactional
    public PlanOrg save(PlanOrg entity){
        Assert.notNull(entity,"planOrg对象不能为 null");
        PlanOrg result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            PlanOrg temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

}
