package com.taiji.emp.nangang.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.entity.QWasteWater;
import com.taiji.emp.nangang.entity.WasteWater;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yhcookie
 * @date 2018/12/10 12:24
 */
@Repository
@Transactional(readOnly = true)
public class WasteWaterRepository extends BaseJpaRepository<WasteWater,String>{

    public WasteWater getWasteWater() {
        QWasteWater wasteWater = QWasteWater.wasteWater;
        JPQLQuery<WasteWater> query = from(wasteWater);
        WasteWater result = query.select(
                Projections.bean(
                        WasteWater.class
                        ,wasteWater.waterFlow
                        ,wasteWater.ph
                        ,wasteWater.cod
                        ,wasteWater.ammoniaNitrogen
                        ,wasteWater.totalNitrogen
                        ,wasteWater.totalPhosphorus
                )).where(wasteWater.nodeId.eq(NangangGlobal.IF_WASTEWATER_NODEID)).orderBy(wasteWater.createTime.desc()).fetchFirst();

        return result;
    }
}
