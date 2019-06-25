package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.PlanMaterial;
import com.taiji.emp.res.entity.QPlanMaterial;
import com.taiji.emp.res.mapper.PlanMaterialMapper;
import com.taiji.emp.res.searchVo.planMaterial.PlanMaterialListVo;
import com.taiji.emp.res.vo.PlanMaterialVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class PlanMaterialRepository extends BaseJpaRepository<PlanMaterial,String> {

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    PlanMaterialMapper mapper;

    //不带分页信息，查询预案物资管理基础列表
    public List<PlanMaterial> findList(PlanMaterialListVo planMaterialListVo){
        QPlanMaterial planMaterial = QPlanMaterial.planMaterial;
        JPQLQuery<PlanMaterial> query = from(planMaterial);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planMaterialListVo.getPlanId();//适用预案
        String eventGradeId = planMaterialListVo.getEventGradeId();//适用事件级别
//        List<String> planIds = planMaterialListVo.getPlanIds();//预案数组

//        if(null!=planIds&&planIds.size()>0){
//            builder.and(planMaterial.planId.in(planIds));
//        }

        if(StringUtils.hasText(planId)){
            builder.and(planMaterial.planId.eq(planId));
        }
        if(StringUtils.hasText(eventGradeId)){
            builder.and(planMaterial.eventGradeId.eq(eventGradeId));
        }

        query.select(
                Projections.bean(PlanMaterial.class
                        ,planMaterial.id
                        ,planMaterial.planId
                        ,planMaterial.eventGradeId
                        ,planMaterial.eventGradeName
                        ,planMaterial.materialTypeId
                        ,planMaterial.itemName
                )).where(builder);
        return findAll(query);
    }

    @Override
    @Transactional
    public PlanMaterial save(PlanMaterial entity){
        Assert.notNull(entity,"PlanMaterial 对象不能为 null");
        PlanMaterial result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            super.deleteAll();
            result = super.save(entity);
        }else{ //编辑保存
            super.deleteAll();
            PlanMaterial temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(entity);
        }
        return result;
    }

    @Transactional
    public List<PlanMaterial> deleteAfterSave(List<PlanMaterial> voList,String planId){
        List<PlanMaterial> list = new ArrayList<>(voList.size());
        //先清空之前的数据再保存
        Query query = this.em.createNativeQuery("delete from EP_PLAN_MATERIAL where plan_id = :planId");
        query.setParameter("planId",planId);
        query.executeUpdate();

        if (null != voList && voList.size() > 0) {
            for (PlanMaterial  entity : voList) {
                PlanMaterial result = super.save(entity);
                if (null != result) {
                    list.add(result);
                }
            }
        }
        return list;
    }

    //根据参数查询预案物资管理
    //参数planId、eventGradeID、expertId
    public List<PlanMaterial> findPlanMaterialByParms(PlanMaterialVo planMaterialVo){
        QPlanMaterial planMaterial = QPlanMaterial.planMaterial;
        JPQLQuery<PlanMaterial> query = from(planMaterial);

        BooleanBuilder builder = new BooleanBuilder();

        String planId = planMaterialVo.getPlanId();//适用预案类别
        //String eventGradeId = planMaterialVo.getEventGradeId();//适用事件级别
       // String materialTypeId = planMaterialVo.getMaterialTypeId();//物资ID

        if(StringUtils.hasText(planId)){
            builder.and(planMaterial.planId.contains(planId));
        }

//        if(StringUtils.hasText(eventGradeId)){
//            builder.and(planMaterial.eventGradeId.contains(eventGradeId));
//        }

//        if(StringUtils.hasText(materialTypeId)){
//            builder.and(planMaterial.materialTypeId.contains(materialTypeId));
//        }

        query.select(
                Projections.bean(PlanMaterial.class
                        ,planMaterial.id
                        ,planMaterial.planId
                        ,planMaterial.eventGradeId
                        ,planMaterial.eventGradeName
                        ,planMaterial.materialTypeId
                        ,planMaterial.itemName
                )).where(builder);
        return findAll(query);
    }

    //根据预案ids查询对应的物资ids
    public List<String> findMaterialsByPlanIds(List<String> planIds){
        QPlanMaterial planMaterial = QPlanMaterial.planMaterial;
        JPQLQuery<PlanMaterial> query = from(planMaterial);

        BooleanBuilder builder = new BooleanBuilder();

        if(null!=planIds&&planIds.size()>0){
            builder.and(planMaterial.planId.in(planIds));
        }
        List<String> experts = query.select(planMaterial.materialTypeId)
                .where(builder)
                .groupBy(planMaterial.materialTypeId)
                .fetch();
        return experts;
    }

}
