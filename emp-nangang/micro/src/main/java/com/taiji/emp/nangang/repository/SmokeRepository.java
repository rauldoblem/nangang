package com.taiji.emp.nangang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.QSmoke;
import com.taiji.emp.nangang.entity.Smoke;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yhcookie
 * @date 2018/12/10 12:23
 */
@Repository
@Transactional(readOnly = true)
public class SmokeRepository extends BaseJpaRepository<Smoke,String>{

    public Smoke getSmoke() {
        QSmoke smoke = QSmoke.smoke;
        JPQLQuery<Smoke> query = from(smoke);
        Smoke result = query.select(
                Projections.bean(
                        Smoke.class
                        , smoke.so2converted
                        , smoke.noconverted
                        , smoke.smokeconverted
                        , smoke.hclconverted
                        , smoke.flow
                        , smoke.createTime
                )).orderBy(smoke.createTime.desc()).fetchOne();
        return result;
    }

    public Page<Smoke> findPage(Pageable page) {
        QSmoke smoke = QSmoke.smoke;
        JPQLQuery<Smoke> query = from(smoke);
        query.select(
                Projections.bean(
                        Smoke.class
                        , smoke.so2converted
                        , smoke.noconverted
                        , smoke.smokeconverted
                        , smoke.hclconverted
                        , smoke.createTime
                )
        ).orderBy(smoke.createTime.desc());
        return findAll(query,page);
    }
}
