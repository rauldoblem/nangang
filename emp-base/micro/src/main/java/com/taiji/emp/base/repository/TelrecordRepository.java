package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.QTelrecord;
import com.taiji.emp.base.entity.Telrecord;
import com.taiji.emp.base.searchVo.telrecord.TelrecordPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public class TelrecordRepository extends BaseJpaRepository<Telrecord,String> {

    //分页查询
    public Page<Telrecord> findPage(TelrecordPageVo telrecordPageVo, Pageable pageable) {
        QTelrecord telrecord = QTelrecord.telrecord;
        JPQLQuery<Telrecord> query = from(telrecord);

        BooleanBuilder builder = new BooleanBuilder();

        String caller = telrecordPageVo.getCaller();
        String callee = telrecordPageVo.getCallee();
        String fileName = telrecordPageVo.getFileName();
        LocalDateTime callBeginTime = telrecordPageVo.getCallBeginTime();
        LocalDateTime callEndTime = telrecordPageVo.getCallEndTime();

        if (StringUtils.hasText(caller)) {
            builder.and(telrecord.caller.contains(caller));
        }
        if (StringUtils.hasText(callee)) {
            builder.and(telrecord.callee.contains(callee));
        }
        if (StringUtils.hasText(fileName)) {
            builder.and(telrecord.fileName.contains(fileName));
        }
        if (null != callBeginTime) {
            builder.and(telrecord.callBeginTime.after(callBeginTime));
        }
        if (null != callEndTime) {
            builder.and(telrecord.callBeginTime.before(callEndTime));
        }

        builder.and(telrecord.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Telrecord.class
                        , telrecord.id
                        , telrecord.caller
                        , telrecord.callee
                        , telrecord.callBeginTime
                        , telrecord.callEndTime
                        , telrecord.callDuration)
        ).where(builder).orderBy(telrecord.updateTime.desc());
        return findAll(query, pageable);
    }
}


