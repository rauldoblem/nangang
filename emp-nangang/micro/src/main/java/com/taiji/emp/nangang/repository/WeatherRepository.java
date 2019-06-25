package com.taiji.emp.nangang.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.entity.QWeather;
import com.taiji.emp.nangang.entity.Weather;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/19 15:54
 */
@Repository
@Transactional(
        readOnly = true
)
public class WeatherRepository extends BaseJpaRepository<Weather,String> {

    /**
     * 根据dailycheckId查询items的list(存的时候组装，查的时候直接拿)
     * 注意返回list的日期顺序
     * @return
     */
    public List<Weather> findAll(String date){

        QWeather weather = QWeather.weather;

        JPQLQuery<Weather> query = from(weather);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(date)){
            builder.and(weather.weatherDate.eq(date));
        }
        query.select(Projections.bean(
                Weather.class
                ,weather.weatherDate
                ,weather.weatherType
                ,weather.high
                ,weather.low
                ,weather.aqi
                ,weather.humidity
                ,weather.temperature
                ,weather.windDirection
                ,weather.windPower
        )).where(builder).orderBy(weather.orderFlag.asc());

        return findAll(query);
    }

    /**
     * 检查 job是否已经在其他服务器执行，已执行返回true
     * 已执行：mysql里边的createtime 在当前时间前一分钟-当前时间后一分钟之间
     * 分布式锁。？
     */
//    public boolean checkJob() {
//        QWeather weather = QWeather.weather;
//
//        JPQLQuery<Weather> query = from(weather);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime before = LocalDateTime.parse(dateFormat.format(System.currentTimeMillis() - 1000*60));
//        BooleanBuilder builder  = new BooleanBuilder();
//        builder.and(weather.createTime.after(before));
//        Weather one = query.select(
//                Projections.bean(
//                        Weather.class
//                        , weather.createTime
//                )).where(builder)
//                .fetchOne();
////        if(null != one){
////            return true;
////        }
//        //先放一放，那就先返回false
//        return false;
//    }
}
