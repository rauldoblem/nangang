package com.taiji.emp.nangang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.entity.Meteorological;
import com.taiji.emp.nangang.entity.QMeteorological;
import com.taiji.emp.nangang.searchVo.meteorological.MeteorologicalPageVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public class MeteorologicalRepository extends BaseJpaRepository<Meteorological,String> {
    public Meteorological getMeteorological(){
        QMeteorological meteorological = QMeteorological.meteorological;
        JPQLQuery<Meteorological> query = from(meteorological);
        Meteorological result = query.select(
                Projections.bean(
                        Meteorological.class
                        , meteorological.temperature
                        , meteorological.atmosphericPressure
                        , meteorological.humidity
                        , meteorological.windSpeed
                        , meteorological.windDirection
                )
        ).where(meteorological.nodeId.eq(NangangGlobal.IF_METEOROLOGICAL_NODEID)).orderBy(meteorological.createTime.desc()).fetchFirst();
        return result;
    }

//    //分页查
//    public Page<Meteorological> findPage(Pageable pageable){
//        QMeteorological meteorological = QMeteorological.meteorological;
//        JPQLQuery<Meteorological> query = from(meteorological);
//        query.select(
//                Projections.bean(
//                        Meteorological.class
//                        , meteorological.temperature
//                        , meteorological.atmosphericPressure
//                        , meteorological.humidity
//                        , meteorological.windSpeed
//                        , meteorological.windDirection
//                        , meteorological.rainfall
//                        , meteorological.createTime
//                )
//        ).orderBy(meteorological.createTime.desc());
//        return findAll(query,pageable);
//    }
}
