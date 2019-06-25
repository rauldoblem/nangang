package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.Fax;
import com.taiji.emp.base.entity.QFax;
import com.taiji.emp.base.searchVo.fax.FaxListVo;
import com.taiji.emp.base.searchVo.fax.FaxPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class FaxRepository extends BaseJpaRepository<Fax,String > {

    //带分页信息，查询传真列表
    public Page<Fax> findPage(FaxPageVo faxPageVo, Pageable pageable){
        QFax fax = QFax.fax;
        JPQLQuery<Fax> query = from(fax);

        BooleanBuilder builder = new BooleanBuilder();

        String title = faxPageVo.getTitle();
        String sender = faxPageVo.getSender();
        String receiver = faxPageVo.getReceiver();
        String faxNumber = faxPageVo.getFaxNumber();
        LocalDateTime createTimeStart = faxPageVo.getCreateTimeStart();
        LocalDateTime createTimeEnd = faxPageVo.getCreateTimeEnd();

        if(StringUtils.hasText(title)){
            builder.and(fax.title.contains(title));
        }
        if(StringUtils.hasText(sender)){
            builder.and(fax.sender.contains(sender));
        }
        if(StringUtils.hasText(receiver)){
            builder.and(fax.receiver.contains(receiver));
        }
        if(StringUtils.hasText(faxNumber)){
            builder.and(fax.faxNumber.eq(faxNumber));
        }

        if(null != createTimeStart){
            builder.and(fax.createTime.after(createTimeStart));
        }

        if (null != createTimeEnd){
            builder.and(fax.createTime.before(createTimeEnd));
        }

        builder.and(fax.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Fax.class
                        ,fax.id
                        ,fax.title
                        ,fax.faxNumber
                        ,fax.sender
                        ,fax.receiver
                        ,fax.sendflag
                        ,fax.ic
                        ,fax.ldc
                        ,fax.createTime
                )).where(builder)
                .orderBy(fax.updateTime.desc());

        return findAll(query,pageable);
    }

    //不带分页信息，查询传真列表
    public List<Fax> findList(FaxListVo faxListVo){
        QFax fax = QFax.fax;
        JPQLQuery<Fax> query = from(fax);

        BooleanBuilder builder = new BooleanBuilder();

        String title = faxListVo.getTitle();
        String sender = faxListVo.getSender();
        String recceiver = faxListVo.getReceiver();
        String faxNumber = faxListVo.getFaxNumber();
        LocalDateTime createTimeStart = faxListVo.getCreateTimeStart();
        LocalDateTime createTimeEnd = faxListVo.getCreateTimeEnd();

        if(StringUtils.hasText(title)){
            builder.and(fax.title.contains(title));
        }
        if(StringUtils.hasText(sender)){
            builder.and(fax.sender.contains(sender));
        }
        if(StringUtils.hasText(recceiver)){
            builder.and(fax.receiver.contains(recceiver));
        }
        if(StringUtils.hasText(faxNumber)){
            builder.and(fax.faxNumber.contains(faxNumber));
        }

        if(null != createTimeStart){
            builder.and(fax.createTime.after(createTimeStart));
        }

        if (null != createTimeEnd){
            builder.and(fax.createTime.before(createTimeEnd));
        }

        builder.and(fax.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Fax.class
                        ,fax.id
                        ,fax.title
                        ,fax.faxNumber
                        ,fax.sender
                        ,fax.receiver
                        ,fax.sendflag
                        ,fax.ic
                        ,fax.ldc
                        ,fax.createTime
                )).where(builder)
                .orderBy(fax.updateTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public Fax save(Fax entity){
        Assert.notNull(entity,"fax 对象不能为 null");

        Fax result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            Fax temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }
}
