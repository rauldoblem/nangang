package com.taiji.emp.nangang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.QSurfaceWater;
import com.taiji.emp.nangang.entity.SurfaceWater;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SurfaceWaterRepository extends BaseJpaRepository<SurfaceWater,String > {
    public SurfaceWater getSurfaceWater(){
        QSurfaceWater surfaceWater = QSurfaceWater.surfaceWater;
        JPQLQuery<SurfaceWater> query = from(surfaceWater);
        SurfaceWater result = query.select(
                Projections.bean(
                        SurfaceWater.class
                        , surfaceWater.waterTemperature
                        , surfaceWater.ph
                        , surfaceWater.conductivity
                        , surfaceWater.turbidity
                        , surfaceWater.dissolvedOxygen
                        , surfaceWater.ammoniaNitrogen
                        , surfaceWater.totalPhosphorus
                        , surfaceWater.totalNitrogen
                        , surfaceWater.totalOrganicNitrogen
                        , surfaceWater.createTime
                )
        ).orderBy(surfaceWater.createTime.desc()).fetchFirst();
        return result;
    }

}
