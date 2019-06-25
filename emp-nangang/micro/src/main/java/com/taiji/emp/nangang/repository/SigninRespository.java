package com.taiji.emp.nangang.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.QSignin;
import com.taiji.emp.nangang.entity.Signin;
import com.taiji.emp.nangang.searchVo.signin.SigninListVo;
import com.taiji.emp.nangang.searchVo.signin.SigninPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SigninRespository extends BaseJpaRepository<Signin,String> {

    //分页查询
    public Page<Signin> findPage(SigninPageVo signinPageVo, Pageable pageable){
        QSignin signin = QSignin.signin;
        JPQLQuery<Signin> query = from(signin);

        BooleanBuilder builder = new BooleanBuilder();

        String dutyPersonName = signinPageVo.getDutyPersonName();
        String dutyDateStart = signinPageVo.getCheckDateStart();
        String dutyDateEnd = signinPageVo.getCheckDateEnd();

        if(StringUtils.hasText(dutyPersonName)){
            builder.and(signin.dutyPersonName.contains(dutyPersonName));
        }

        if(!StringUtils.isEmpty(dutyDateStart) && StringUtils.isEmpty(dutyDateEnd)){
            LocalDate start = DateUtil.strToLocalDate(dutyDateStart);
            builder.and(signin.dutyDate.goe(start));
        }

        if(!StringUtils.isEmpty(dutyDateEnd) && StringUtils.isEmpty(dutyDateStart)){
            LocalDate end = DateUtil.strToLocalDate(dutyDateEnd);
            builder.and(signin.dutyDate.loe(end));
        }

        if(!StringUtils.isEmpty(dutyDateStart) && !StringUtils.isEmpty(dutyDateEnd)){
            LocalDate start = DateUtil.strToLocalDate(dutyDateStart);
            LocalDate end = DateUtil.strToLocalDate(dutyDateEnd);
            builder.and(signin.dutyDate.goe(start))
                    .and(signin.dutyDate.loe(end));
        }
        builder.and(signin.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Signin.class
                        ,signin.id
                        ,signin.dutyDate
                        ,signin.dutyShiftPattern
                        ,signin.dutyPersonId
                        ,signin.dutyPersonName
                        ,signin.signStatus
                        ,signin.checkInTime
                        ,signin.checkOutTime
                )).where(builder)
                .orderBy(signin.createTime.desc());

        return findAll(query,pageable);
    }

    //不带分页信息查询
    public List<Signin> findList(SigninListVo signinListVo){
        QSignin signin = QSignin.signin;
        JPQLQuery<Signin> query = from(signin);

        BooleanBuilder builder = new BooleanBuilder();
        String flag = signinListVo.getFlag();
        LocalDate dutyDate = signinListVo.getDutyDate();
        String dutyShiftPattern = signinListVo.getDutyShiftPattern();
        String dutyPersonId = signinListVo.getDutyPersonId();
        List<String> dutyPersonIds = signinListVo.getDutyPersonIds();
        String userName = signinListVo.getUserName();
        LocalDateTime startTime = signinListVo.getStartTime();
        LocalDateTime endTime = signinListVo.getEndTime();
        if (!StringUtils.isEmpty(flag)) {
            if (flag.equals("0")) {
                if (null != startTime && null != endTime ) {
                    builder.and(signin.checkInTime.between(startTime,endTime));
                }
                if (!CollectionUtils.isEmpty(dutyPersonIds)) {
                    builder.and(signin.dutyPersonId.in(dutyPersonIds));
                }
                if (null != userName) {
                    builder.and(signin.createBy.eq(userName));
                }

                builder.and(signin.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
                builder.and(signin.checkInTime.isNotNull());
                query.select(
                        Projections.bean(Signin.class
                                , signin.id
                                , signin.dutyDate
                                , signin.dutyShiftPattern
                                , signin.dutyPersonId
                                , signin.dutyPersonName
                                , signin.signStatus
                                , signin.checkInTime
                                , signin.checkOutTime
                        )).where(builder)
                        .orderBy(signin.updateTime.desc());
                }
            } else {
                if (null != dutyDate) {
                    builder.and(signin.dutyDate.eq(dutyDate));
                }
                if (StringUtils.hasText(dutyShiftPattern)) {
                    builder.and(signin.dutyShiftPattern.eq(dutyShiftPattern));
                }
                if (StringUtils.hasText(dutyPersonId)) {
                    builder.and(signin.dutyPersonId.eq(dutyPersonId));
                }

                builder.and(signin.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
                query.select(
                        Projections.bean(Signin.class
                                , signin.id
                                , signin.dutyDate
                                , signin.dutyShiftPattern
                                , signin.dutyPersonId
                                , signin.dutyPersonName
                                , signin.signStatus
                                , signin.checkInTime
                                , signin.checkOutTime
                        )).where(builder)
                        .orderBy(signin.updateTime.desc());
            }

        return findAll(query);
    }
    @Override
    @Transactional
    public Signin save(Signin entity){
        Assert.notNull(entity,"signin 对象不能为 null");

        Signin result;
        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else{ //编辑保存
            Signin temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }

        return result;
    }
}
