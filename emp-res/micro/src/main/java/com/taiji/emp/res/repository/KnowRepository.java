package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.res.entity.Knowledge;
import com.taiji.emp.res.entity.QKnowledge;
import com.taiji.emp.res.searchVo.knowledge.KnowListVo;
import com.taiji.emp.res.searchVo.knowledge.KnowPageVo;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class KnowRepository extends BaseJpaRepository<Knowledge,String> {

    //带分页信息，查询应急知识列表
    public Page<Knowledge> findPage(KnowPageVo knowPageVo, Pageable pageable){
        QKnowledge knowledge = QKnowledge.knowledge;
        JPQLQuery<Knowledge> query = from(knowledge);

        BooleanBuilder builder = new BooleanBuilder();

        String title = knowPageVo.getTitle();//标题
        List<String> eventTypeIds = knowPageVo.getEventTypeIds();//适用事件类别：传入数组(多选)
        String keyWord = knowPageVo.getKeyWord();//关键词
        String knoTypeId = knowPageVo.getKnoTypeId();//知识类型
        String createOrgId = knowPageVo.getCreateOrgId(); //创建单位

        if(StringUtils.hasText(title)){
            builder.and(knowledge.title.contains(title));
        }
        if(null!=eventTypeIds&&eventTypeIds.size()>0){
            builder.and(knowledge.eventTypeId.in(eventTypeIds));
        }
        if(StringUtils.hasText(keyWord)){
            builder.and(knowledge.keyWord.contains(keyWord));
        }
        if(StringUtils.hasText(knoTypeId)){
            builder.and(knowledge.knoTypeId.eq(knoTypeId));
        }

        if(StringUtils.hasText(createOrgId)){
            builder.and(knowledge.createOrgId.eq(createOrgId));
        }

        builder.and(knowledge.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Knowledge.class
                        ,knowledge.id
                        ,knowledge.title
//                        ,knowledge.knoTypeId
                        ,knowledge.knoTypeName
//                        ,knowledge.eventTypeId
                        ,knowledge.eventTypeName
//                        ,knowledge.source
//                        ,knowledge.keyWord
                        ,knowledge.content
                        ,knowledge.createOrgId
//                        ,knowledge.createOrgName
                )).where(builder)
                .orderBy(knowledge.updateTime.desc());

        return findAll(query,pageable);
    }

    //不带分页信息，查询应急知识列表
    public List<Knowledge> findList(KnowListVo knowListVo){
        QKnowledge knowledge = QKnowledge.knowledge;
        JPQLQuery<Knowledge> query = from(knowledge);

        BooleanBuilder builder = new BooleanBuilder();

        String title = knowListVo.getTitle();//标题
        List<String> eventTypeIds = knowListVo.getEventTypeIds();//适用事件类别：传入数组(多选)
        String keyWord = knowListVo.getKeyWord();//关键词
        String knoTypeId = knowListVo.getKnoTypeId();//知识类型
        String createOrgId = knowListVo.getCreateOrgId();//创建单位

        if(StringUtils.hasText(title)){
            builder.and(knowledge.title.contains(title));
        }
        if(null!=eventTypeIds&eventTypeIds.size()>0){
            builder.and(knowledge.eventTypeId.in(eventTypeIds));
        }
        if(StringUtils.hasText(keyWord)){
            builder.and(knowledge.keyWord.contains(keyWord));
        }
        if(StringUtils.hasText(knoTypeId)){
            builder.and(knowledge.knoTypeId.eq(knoTypeId));
        }
        if(StringUtils.hasText(createOrgId)){
            builder.and(knowledge.createOrgId.eq(createOrgId));
        }

        builder.and(knowledge.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Knowledge.class
                        ,knowledge.id
                        ,knowledge.title
                        ,knowledge.knoTypeId
                        ,knowledge.knoTypeName
                        ,knowledge.eventTypeId
                        ,knowledge.eventTypeName
                        ,knowledge.source
                        ,knowledge.keyWord
                        ,knowledge.content
                        ,knowledge.createOrgId
                        ,knowledge.createOrgName
                )).where(builder)
                .orderBy(knowledge.updateTime.desc());
        return findAll(query);
    }

    @Override
    @Transactional
    public Knowledge save(Knowledge entity){
        Assert.notNull(entity,"knowledge 对象不能为 null");

        Knowledge result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            Knowledge temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }
}
