package com.taiji.emp.event.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.emp.event.cmd.entity.QScheme;
import com.taiji.emp.event.cmd.entity.Scheme;
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
public class SchemeRepository extends BaseJpaRepository<Scheme,String> {

    //保存方法
    @Override
    @Transactional
    public Scheme save(Scheme entity){
        Assert.notNull(entity,"Analyse 不能为null");
        String id = entity.getId();
        Scheme result;
        if(StringUtils.isEmpty(id)){
            Scheme oldScheme = findByEventId(entity.getEventId());
            if(null!=oldScheme){
                return null; //已经存在该方案记录，避免重复入库
            }
            result = super.save(entity);
        }else{
            Scheme temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    //根据eventId查询唯一方案
    public Scheme findByEventId(String eventId){
        Assert.hasText(eventId,"eventId 不能为空字符串");
        QScheme scheme = QScheme.scheme;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(scheme.eventId.eq(eventId));
        builder.and(scheme.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        List<Scheme> list = findAll(builder);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

}
