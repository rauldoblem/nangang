package com.taiji.emp.event.cmd.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.cmd.entity.Analyse;
import com.taiji.emp.event.cmd.entity.QAnalyse;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
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
public class AnalyseRepository extends BaseJpaRepository<Analyse,String>{

    //保存方法
    @Override
    @Transactional
    public Analyse save(Analyse entity){
        Assert.notNull(entity,"Analyse 不能为null");
        String id = entity.getId();
        Analyse result;
        if(StringUtils.isEmpty(id)){
            result = super.save(entity);
        }else{
            Analyse temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    public List<Analyse> findList(MultiValueMap<String,Object> params){

        QAnalyse analyse = QAnalyse.analyse;
        JPQLQuery<Analyse> query = from(analyse);
        BooleanBuilder builder = new BooleanBuilder();

        if(params.containsKey("eventId")){
            builder.and(analyse.eventId.eq(params.getFirst("eventId").toString()));
        }

        builder.and(analyse.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(Analyse.class
                ,analyse.id
                ,analyse.analyseResult
                ,analyse.createTime
                )).where(builder).orderBy(analyse.updateTime.desc());

        return findAll(query);
    }


}
