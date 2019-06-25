package com.taiji.emp.res.repository;

import com.mysema.commons.lang.Assert;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.Law;
import com.taiji.emp.res.entity.QLaw;
import com.taiji.emp.res.searchVo.law.LawListVo;
import com.taiji.emp.res.searchVo.law.LawPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class LawRepository extends BaseJpaRepository<Law,String> {

    //带分页查询
    public Page<Law> findPage(LawPageVo lawPageVo, Pageable pageable){
        QLaw law = QLaw.law;
        JPQLQuery<Law> query = from(law);

        BooleanBuilder builder = new BooleanBuilder();

        String title = lawPageVo.getTitle();
        String keyWord = lawPageVo.getKeyWord();
        String buildOrg = lawPageVo.getBuildOrg();
        List<String> eventTypeIds = lawPageVo.getEventTypeIds();
        List<String> lawTypeIds = lawPageVo.getLawTypeIds();

        if (StringUtils.hasText(title)){
            builder.and(law.title.contains(title));
        }
        if (StringUtils.hasText(keyWord)){
            builder.and(law.keyWord.contains(keyWord));
        }
        if (StringUtils.hasText(buildOrg)){
            builder.and(law.buildOrg.contains(buildOrg));
        }
        if (null!=eventTypeIds&&eventTypeIds.size()>0){
            builder.and(law.eventTypeId.in(eventTypeIds));
        }
        if (null != lawTypeIds && lawTypeIds.size()>0){
            builder.and(law.lawTypeId.in(lawTypeIds));
        }

        builder.and(law.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Law.class
                        ,law.id
                        ,law.title
                        ,law.buildOrg
                        ,law.lawTypeId
                        ,law.lawTypeName
                        ,law.eventTypeId
                        ,law.eventTypeName
                        ,law.keyWord
                )).where(builder)
                .orderBy(law.updateTime.desc());
        return findAll(query,pageable);
    }
    // 不带分页查询
    public List<Law> findList(LawListVo lawListVo) {
        QLaw law = QLaw.law;
        JPQLQuery<Law> query = from(law);

        BooleanBuilder builder = new BooleanBuilder();

        String title = lawListVo.getTitle();
        String keyWord = lawListVo.getKeyWord();
        String buildOrg = lawListVo.getBuildOrg();
        List<String> eventTypeIds = lawListVo.getEventTypeIds();
        List<String> lawTypeIds = lawListVo.getLawTypeIds();

        if (StringUtils.hasText(title)){
            builder.and(law.title.contains(title));
        }
        if (StringUtils.hasText(keyWord)){
            builder.and(law.keyWord.contains(keyWord));
        }
        if (StringUtils.hasText(buildOrg)){
            builder.and(law.buildOrg.contains(buildOrg));
        }
        if (null!=eventTypeIds&&eventTypeIds.size()>0){
            builder.and(law.eventTypeId.in(eventTypeIds));
        }
        if (null != lawTypeIds && lawTypeIds.size()>0){
            builder.and(law.lawTypeId.in(lawTypeIds));
        }

        builder.and(law.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Law.class
                        ,law.id
                        ,law.title
                        ,law.buildOrg
                        ,law.lawTypeId
                        ,law.lawTypeName
                        ,law.eventTypeId
                        ,law.eventTypeName
                        ,law.keyWord
                )).where(builder)
                .orderBy(law.updateTime.desc());

        return findAll(query);
    }



    @Override
    @Transactional
    public Law save(Law entity){
        Assert.notNull(entity,"law对象不能为null");

        Law result;
        if (StringUtils.isEmpty(entity.getId())) {
            result = super.save(entity);
        }else {
            Law temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }
}
