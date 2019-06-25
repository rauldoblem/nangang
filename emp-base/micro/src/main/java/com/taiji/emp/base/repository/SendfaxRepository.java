package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.Fax;
import com.taiji.emp.base.entity.QSendfax;
import com.taiji.emp.base.entity.Sendfax;
import com.taiji.emp.base.searchVo.fax.SendfaxPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
@Repository
@Transactional(readOnly = true)
public class SendfaxRepository extends BaseJpaRepository<Sendfax,String> {
    //带分页信息，查询已发送传真列表
    public Page<Sendfax> findPage(SendfaxPageVo sendfaxPageVo, Pageable pageable){
        QSendfax sendfax = QSendfax.sendfax;
        JPQLQuery<Sendfax> query = from(sendfax);

        BooleanBuilder builder = new BooleanBuilder();

        String title = sendfaxPageVo.getTitle();
        String receiver = sendfaxPageVo.getReceiver();
        String faxNumber = sendfaxPageVo.getFaxNumber();
        LocalDateTime sendTimeStart = sendfaxPageVo.getSendTimeStart();
        LocalDateTime sendTimeEnd = sendfaxPageVo.getSendTimeEnd();

        if(StringUtils.hasText(title)){
            builder.and(sendfax.title.contains(title));
        }
        if(StringUtils.hasText(receiver)){
            builder.and(sendfax.sender.contains(receiver));
        }
        if(StringUtils.hasText(faxNumber)){
            builder.and(sendfax.faxNumber.eq(faxNumber));
        }

        if(null != sendTimeStart){
            builder.and(sendfax.sendtime.after(sendTimeStart));
        }

        if (null != sendTimeEnd){
            builder.and(sendfax.sendtime.before(sendTimeEnd));
        }

        builder.and(sendfax.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Sendfax.class
                        ,sendfax.id
                        ,sendfax.title
                        ,sendfax.faxNumber
                        ,sendfax.sender
                        ,sendfax.receiver
                        ,sendfax.sendtime
                )).where(builder)
                .orderBy(sendfax.updateTime.desc());

        return findAll(query,pageable);
    }
}
