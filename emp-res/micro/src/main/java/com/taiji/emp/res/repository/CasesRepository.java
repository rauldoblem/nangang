package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.CaseEntity;
import com.taiji.emp.res.entity.QCaseEntity;
import com.taiji.emp.res.searchVo.caseVo.CasePageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class CasesRepository extends BaseJpaRepository<CaseEntity,String> {

    //带分页信息，查询危险源列表
    public Page<CaseEntity> findPage(CasePageVo casePageVo, Pageable pageable){
        JPQLQuery<CaseEntity> query = buildQuery(casePageVo);
        return findAll(query,pageable);
    }

    //不带分页信息，查询危险源列表
    public List<CaseEntity> findList(CasePageVo casePageVo){
        JPQLQuery<CaseEntity> query = buildQuery(casePageVo);
        return findAll(query);
    }

    private JPQLQuery<CaseEntity> buildQuery(CasePageVo casePageVo){
        QCaseEntity caseEntity = QCaseEntity.caseEntity;
        JPQLQuery<CaseEntity> query = from(caseEntity);
        BooleanBuilder builder = new BooleanBuilder();
        String createOrgId = casePageVo.getCreateOrgId();
        String eventGradeId = casePageVo.getEventGradeId();
        List<String> eventTypeIds =  casePageVo.getEventTypeIds();
        String occurEndTime =  casePageVo.getOccurEndTime();
        String occurStartTime =  casePageVo.getOccurStartTime();
        String sourceFlag =  casePageVo.getSourceFlag();
        String title =   casePageVo.getTitle();
        if(StringUtils.hasText(title)){
            builder.and(caseEntity.title.contains(title));
        }
        if(StringUtils.hasText(eventGradeId)){
            builder.and(caseEntity.eventGradeId.contains(eventGradeId));
        }
        if(StringUtils.hasText(sourceFlag)){
            builder.and(caseEntity.sourceFlag.contains(sourceFlag));
        }
        if(StringUtils.hasText(occurStartTime)&&StringUtils.hasText(occurEndTime)){
            builder.and(caseEntity.occurTime.between(
                    DateUtil.strToLocalDateTime(occurStartTime),DateUtil.strToLocalDateTime(occurEndTime)));
        }
        if (!StringUtils.hasText(occurStartTime) && StringUtils.hasText(occurEndTime)){
            builder.and(caseEntity.occurTime.loe(DateUtil.strToLocalDateTime(occurEndTime)));
        }
        if (!StringUtils.hasText(occurEndTime) && StringUtils.hasText(occurStartTime)){
            builder.and(caseEntity.occurTime.goe(DateUtil.strToLocalDateTime(occurStartTime)));
        }
        if(StringUtils.hasText(createOrgId)){
            builder.and(caseEntity.createOrgId.contains(createOrgId));
        }
        if(null!=eventTypeIds&&eventTypeIds.size()>0){
                builder.and(caseEntity.eventTypeId.in(eventTypeIds));
        }
        builder.and(caseEntity.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(CaseEntity.class
                        ,caseEntity.id
                        ,caseEntity.title
                        ,caseEntity.eventId
                        ,caseEntity.eventName
                        ,caseEntity.position
                        ,caseEntity.occurTime
                        ,caseEntity.eventTypeId
                        ,caseEntity.eventTypeName
                        ,caseEntity.eventGradeId
                        ,caseEntity.eventGradeName
                        ,caseEntity.sourceFlag
                        ,caseEntity.caseSource
                        ,caseEntity.describes
                        ,caseEntity.notes
                        ,caseEntity.createOrgId
                )).where(builder)
                .orderBy(caseEntity.updateTime.desc());
        return query;
    }

    @Override
    @Transactional
    public CaseEntity save(CaseEntity entity){
        Assert.notNull(entity,"CaseEntity 对象不能为 null");

        CaseEntity result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            CaseEntity temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }
}
