package com.taiji.emp.event.infoDispatch.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.infoDispatch.entity.Accept;
import com.taiji.emp.event.infoDispatch.entity.QAccept;
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
public class AcceptRepository extends BaseJpaRepository<Accept,String> {

    @Override
    @Transactional
    public Accept save(Accept entity){
        Assert.notNull(entity,"Accept 不能为null");
        String id = entity.getId();
        Accept result;
        if(StringUtils.isEmpty(id)){//新增保存
            result= super.save(entity);
        }else{
            Accept temp = super.findOne(id);
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 用于主表中通过eventId查询所有初报续报对象
     */
    public List<Accept> findAllByIds(List<String> acceptIds){

        QAccept accept = QAccept.accept;
        JPQLQuery<Accept> query = from(accept);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(accept.id.in(acceptIds));
        builder.and(accept.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder)
                .orderBy(accept.reportTime.desc());

        return findAll(query);
    }

}
