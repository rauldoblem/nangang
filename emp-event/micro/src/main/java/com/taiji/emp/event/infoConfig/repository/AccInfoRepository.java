package com.taiji.emp.event.infoConfig.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.event.infoConfig.entity.AcceptInform;
import com.taiji.emp.event.infoConfig.entity.QAcceptInform;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class AccInfoRepository extends BaseJpaRepository<AcceptInform,String> {


    @Transactional
    @Override
    public AcceptInform save(AcceptInform entity){
        Assert.notNull(entity,"AcceptInform对象不能为 null");
        AcceptInform result;
        String id = entity.getId();
        if(StringUtils.isEmpty(id)){ //新增保存
            result = super.save(entity);
        }else {//编辑保存
            AcceptInform temp = super.findOne(id);
            Assert.notNull(temp,"temp对象不能为 null");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }


    public List<AcceptInform> findList(MultiValueMap<String,Object> params){

        QAcceptInform acceptInform = QAcceptInform.acceptInform;
        JPQLQuery<AcceptInform> query = from(acceptInform);
        BooleanBuilder builder = new BooleanBuilder();

        if(params.containsKey("eventTypeId")){
            builder.and(acceptInform.eventTypeId.eq(params.getFirst("eventTypeId").toString()));
        }else{
            return new ArrayList<>();
        }
        query.where(builder).orderBy(acceptInform.orders.asc());
        return findAll(query);
    }

}
