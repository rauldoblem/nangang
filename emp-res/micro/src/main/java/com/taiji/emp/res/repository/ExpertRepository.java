package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.Expert;
import com.taiji.emp.res.entity.QExpert;
import com.taiji.emp.res.searchVo.expert.ExpertListVo;
import com.taiji.emp.res.searchVo.expert.ExpertPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class ExpertRepository extends BaseJpaRepository<Expert,String>{

    //查询应急专家list -- 分页
    public Page<Expert> findPage(ExpertPageVo expertPageVo, Pageable pageable){

        String name = expertPageVo.getName();
        String unit = expertPageVo.getUnit();
        List<String> eventTypeIds = expertPageVo.getEventTypeIds();
        String specialty = expertPageVo.getSpecialty();
        String createOrgId = expertPageVo.getCreateOrgId();
        List<String> expertIds = expertPageVo.getExpertIds();
        //已选专家IDS，在结果列表中，将这些专家去掉（预案数字化时使用）
        List<String> selectedExpertIds = expertPageVo.getSelectedExpertIds();

        QExpert expert = QExpert.expert;

        JPQLQuery<Expert> query = from(expert);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(name)){//姓名
            builder.and(expert.name.contains(name));
        }

        if(StringUtils.hasText(unit)){//工作单位
            builder.and(expert.unit.contains(unit));
        }

        if(null!=eventTypeIds&&eventTypeIds.size()>0){//适用事件类型id串
            BooleanBuilder eventTypeBuilder = new BooleanBuilder();
            for(String eventType : eventTypeIds){ //组装or条件
                eventTypeBuilder.or(expert.eventTypeIds.contains(eventType));
            }
            builder.and(eventTypeBuilder);
        }

        //预案数字化查询相应的专家信息
        if(null!=expertIds&&expertIds.size()>0){
            builder.and(expert.id.in(expertIds));
        }
        if(StringUtils.hasText(specialty)){
            builder.and(expert.specialty.contains(specialty));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(expert.createOrgId.eq(createOrgId));
        }

        if(null!=selectedExpertIds&&selectedExpertIds.size()>0){
            builder.and(expert.id.notIn(selectedExpertIds));
        }

        builder.and(expert.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Expert.class
                ,expert.id
                ,expert.name
//                ,expert.sex
//                ,expert.birthday
//                ,expert.education
                ,expert.unit
//                ,expert.post
//                ,expert.technicalTitle
//                ,expert.officeTel
                ,expert.telephone
//                ,expert.email
//                ,expert.address
//                ,expert.eventTypeIds
                ,expert.eventTypeNames
                ,expert.specialty
//                ,expert.notes
//                ,expert.photoUrl
                ,expert.createOrgId
        )).where(builder)
                .orderBy(expert.updateTime.desc());


        return findAll(query,pageable);
    }

    //查询应急专家list -- 不分页
    public List<Expert> findList(ExpertListVo expertListVo){

        String name = expertListVo.getName();
        String unit = expertListVo.getUnit();
        List<String> eventTypeIds = expertListVo.getEventTypeIds();
        String specialty = expertListVo.getSpecialty();
        String createOrgId = expertListVo.getCreateOrgId();
        List<String> expertIds = expertListVo.getExpertIds();
        //已选专家IDS，在结果列表中，将这些专家去掉（预案数字化时使用）
        List<String> selectedExpertIds = expertListVo.getSelectedExpertIds();

        QExpert expert = QExpert.expert;

        JPQLQuery<Expert> query = from(expert);
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(name)){//姓名
            builder.and(expert.name.contains(name));
        }

        if(StringUtils.hasText(unit)){//工作单位
            builder.and(expert.unit.contains(unit));
        }

        if(null!=eventTypeIds&&eventTypeIds.size()>0){//适用事件类型id串
            BooleanBuilder eventTypeBuilder = new BooleanBuilder();
            for(String eventType : eventTypeIds){ //组装or条件
                eventTypeBuilder.or(expert.eventTypeIds.contains(eventType));
            }
            builder.and(eventTypeBuilder);
        }

        //预案数字化查询相应的专家信息
        if(null!=expertIds&&expertIds.size()>0){
            builder.and(expert.id.in(expertIds));
        }

        if(StringUtils.hasText(specialty)){
            builder.and(expert.specialty.contains(specialty));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(expert.createOrgId.eq(createOrgId));
        }

        if(null!=selectedExpertIds&&selectedExpertIds.size()>0){
            builder.and(expert.id.notIn(selectedExpertIds));
        }

        builder.and(expert.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Expert.class
                ,expert.id
                ,expert.name
                ,expert.sex
                ,expert.birthday
                ,expert.education
                ,expert.unit
                ,expert.post
                ,expert.technicalTitle
                ,expert.officeTel
                ,expert.telephone
                ,expert.email
                ,expert.address
                ,expert.eventTypeIds
                ,expert.eventTypeNames
                ,expert.specialty
                ,expert.notes
                ,expert.photoUrl
                ,expert.createOrgId
        )).where(builder)
                .orderBy(expert.updateTime.desc());
        return findAll(query);
    }

    @Override
    @Transactional
    public Expert save(Expert entity){
        Assert.notNull(entity,"expert对象不能为 null");
        Expert result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else{//编辑保存
            Expert temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

}
