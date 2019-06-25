package com.taiji.emp.nangang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.entity.ConventionalFactor;
import com.taiji.emp.nangang.entity.QConventionalFactor;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ConventionalFactorRepository extends BaseJpaRepository<ConventionalFactor,String> {
    public ConventionalFactor getConventionalFactor() {
        QConventionalFactor conventionalFactor = QConventionalFactor.conventionalFactor;
        JPQLQuery<ConventionalFactor> query = from(conventionalFactor);
        ConventionalFactor result = query.select(
                Projections.bean(
                        ConventionalFactor.class
                        , conventionalFactor.pm2_5
                        , conventionalFactor.pm10
                        , conventionalFactor.no2
                        , conventionalFactor.co
                )).where(conventionalFactor.nodeId.eq(NangangGlobal.IF_METEOROLOGICAL_NODEID)).orderBy(conventionalFactor.createTime.desc()).fetchFirst();
        return result;
    }

    /*
     * 分页查询
     */
    public Page<ConventionalFactor> findPage( Pageable pageable){
        QConventionalFactor conventionalFactor = QConventionalFactor.conventionalFactor;
        JPQLQuery<ConventionalFactor> query = from(conventionalFactor);
        query.select(
                Projections.bean(
                        ConventionalFactor.class
                        ,conventionalFactor.so2
                        ,conventionalFactor.pm2_5
                        ,conventionalFactor.o3
                        ,conventionalFactor.no2
                        ,conventionalFactor.pm10
                        ,conventionalFactor.createTime
                )).where(conventionalFactor.nodeId.eq(NangangGlobal.IF_METEOROLOGICAL_NODEID)).orderBy(conventionalFactor.createTime.desc());
        return findAll(query,pageable);
    }
}
