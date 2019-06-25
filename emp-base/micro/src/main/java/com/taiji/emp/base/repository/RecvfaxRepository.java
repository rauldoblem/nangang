package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.Fax;
import com.taiji.emp.base.entity.QRecvfax;
import com.taiji.emp.base.entity.Recvfax;
import com.taiji.emp.base.searchVo.fax.RecvfaxPageVo;
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
public class RecvfaxRepository extends BaseJpaRepository<Recvfax,String> {
    //带分页信息，查询已接收传真列表
    public Page<Recvfax> findPage(RecvfaxPageVo recvfaxPageVo, Pageable pageable){
        QRecvfax recvfax = QRecvfax.recvfax;
        JPQLQuery<Recvfax> query = from(recvfax);

        BooleanBuilder builder = new BooleanBuilder();

        String title = recvfaxPageVo.getTitle();
        String sender = recvfaxPageVo.getSender();
        String faxNumber = recvfaxPageVo.getFaxNumber();
        LocalDateTime recvTimeStart = recvfaxPageVo.getRecvTimeStart();
        LocalDateTime recvTimeEnd = recvfaxPageVo.getRecvTimeEnd();

        if(StringUtils.hasText(title)){
            builder.and(recvfax.title.contains(title));
        }
        if(StringUtils.hasText(sender)){
            builder.and(recvfax.sender.contains(sender));
        }
        if(StringUtils.hasText(faxNumber)){
            builder.and(recvfax.faxNumber.eq(faxNumber));
        }

        if(null != recvTimeStart){
            builder.and(recvfax.recvtime.after(recvTimeStart));
        }

        if (null != recvTimeEnd){
            builder.and(recvfax.recvtime.before(recvTimeEnd));
        }

        builder.and(recvfax.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Recvfax.class
                        ,recvfax.id
                        ,recvfax.title
                        ,recvfax.faxNumber
                        ,recvfax.sender
                        ,recvfax.receiver
                        ,recvfax.recvtime
                )).where(builder)
                .orderBy(recvfax.updateTime.desc());

        return findAll(query,pageable);
    }

}
