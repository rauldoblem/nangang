package com.taiji.emp.nangang.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.nangang.common.constant.NangangGlobal;
import com.taiji.emp.nangang.entity.PassRecordVehicle;
import com.taiji.emp.nangang.entity.QPassRecordVehicle;
import com.taiji.emp.nangang.vo.VehicleNumberOfCompanyVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.repository.UtilsRepository;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yhcookie
 * @date 2018/12/5 11:38
 */
@Repository
public class PassRecordVehicleRepository extends BaseJpaRepository<PassRecordVehicle,String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    UtilsRepository utilsRepository;
    /**
     * 大屏页面二
     * @return
     */
    public List<PassRecordVehicle> getVehicleTypeAndFlow() {
        QPassRecordVehicle passRecordVehicle = QPassRecordVehicle.passRecordVehicle;
        JPQLQuery<PassRecordVehicle> query = from(passRecordVehicle);

        JPQLQuery<PassRecordVehicle> select = query.select(
                Projections.bean(PassRecordVehicle.class
                        , passRecordVehicle.vehicleType
                        , passRecordVehicle.inOutFlag
                        , passRecordVehicle.totalVehicle));
        //从这里开始,先不处理了
        List<PassRecordVehicle> queryResults = findAll(select);

        //2019-2-19新增：把其他车辆的数据合并到施工车辆中
        //1.分别把施工车辆和其他车辆的出和入的结果拿出来，并从结果集中移除掉
        PassRecordVehicle shigongVehicleIn = null;
        PassRecordVehicle otherVehicleIn = null;
        PassRecordVehicle shigongVehicleOut = null;
        PassRecordVehicle otherVehicleOut = null;
        if(!CollectionUtils.isEmpty(queryResults)){
        for (PassRecordVehicle queryResult : queryResults) {
            if (null != queryResult) {
                String vehicleType = queryResult.getVehicleType();
                String inOutFlag = queryResult.getInOutFlag();
                if (NangangGlobal.SHIGONG_VIHICLE.equals(vehicleType)) {
                    if (NangangGlobal.IN_OUT_FLAG_IN.equals(inOutFlag)) {
                        shigongVehicleIn = queryResult;
                    } else if (NangangGlobal.IN_OUT_FLAG_OUT.equals(inOutFlag)) {
                        shigongVehicleOut = queryResult;
                    }
                } else if (NangangGlobal.OTHER_VIHICLE.equals(vehicleType)) {
                    if (NangangGlobal.IN_OUT_FLAG_IN.equals(inOutFlag)) {
                        otherVehicleIn = queryResult;
                    } else if (NangangGlobal.IN_OUT_FLAG_OUT.equals(inOutFlag)) {
                        otherVehicleOut = queryResult;
                    }
                }
            }
        }

        if (null != shigongVehicleIn) queryResults.remove(shigongVehicleIn);
        if (null != shigongVehicleOut) queryResults.remove(shigongVehicleOut);
        if (null != otherVehicleIn) queryResults.remove(otherVehicleIn);
        if (null != otherVehicleOut) queryResults.remove(otherVehicleOut);
        //2.将其他车辆数据合并到施工车辆中 并 添加到结果集中
        if (null != shigongVehicleIn) {
            if (null != otherVehicleIn) {
                String totalShigong = shigongVehicleIn.getTotalVehicle();
                String totalOther = otherVehicleIn.getTotalVehicle();
                if (!StringUtils.isEmpty(totalShigong) && !StringUtils.isEmpty(totalOther)) {
                    totalShigong = String.valueOf(Integer.parseInt(totalShigong) + Integer.parseInt(totalOther));
                }
                shigongVehicleIn.setTotalVehicle(totalShigong);
            }
            queryResults.add(shigongVehicleIn);
        }

        if (null != shigongVehicleOut) {
            if (null != otherVehicleOut) {
                String totalShigong = shigongVehicleOut.getTotalVehicle();
                String totalOther = otherVehicleOut.getTotalVehicle();
                if (!StringUtils.isEmpty(totalShigong) && !StringUtils.isEmpty(totalOther)) {
                    totalShigong = String.valueOf(Integer.parseInt(totalShigong) + Integer.parseInt(totalOther));
                }
                shigongVehicleOut.setTotalVehicle(totalShigong);
            }
            queryResults.add(shigongVehicleOut);
        }
    }
        //boolean empty = CollectionUtils.isEmpty(queryResults);
        //Assert.isTrue(!empty , "查询结果queryResults 为空");
        return queryResults;
    }
//    public List<PassRecordVehicle> getVehicleTypeAndFlow() {
//        QPassRecordVehicle passRecordVehicle = QPassRecordVehicle.passRecordVehicle;
//        JPQLQuery<PassRecordVehicle> query = from(passRecordVehicle);
//
//        JPQLQuery<PassRecordVehicle> select = query.select(
//                Projections.bean(PassRecordVehicle.class
//                        , passRecordVehicle.vehicleType
//                        , passRecordVehicle.inOutFlag
//                        , passRecordVehicle.totalVehicle));
//        //从这里开始,先不处理里了
//        List<PassRecordVehicle> queryResults = findAll(select);
//        boolean empty = CollectionUtils.isEmpty(queryResults);
//        Assert.isTrue(!empty , "查询结果queryResults 为空");
//        return queryResults;
//    }

    /**
     * 大屏页面一 卡口名称以及出入量
     * @return
     */
    public List<PassRecordVehicle> getBayonetVehiclePassState() {

        //mysql支持`` ，oracle不支持
        String sql = "SELECT sum(HongQi_Road) HongQi_Road," +
                "sum(HaiGang_South_Road) HaiGang_South_Road," +
                "sum(HaiGang_North_Road) HaiGang_North_Road," +
                "sum(NanTi_Road) NanTi_Road," +
                "sum(HaiFang_North_Road) HaiFang_North_Road," +
                "sum(HaiFang_Middle_Road) HaiFang_Middle_Road," +
                "sum(HaiFang_South_Road) HaiFang_South_Road," +
                "sum(ChuangYe_Road) ChuangYe_Road," +
                "sum(BinHai_North_Road) BinHai_North_Road " +
                "FROM ng_pass_record_vehicle " +
                "where IN_OUT_FLAG = ?";
        List results = jdbcTemplate.query(sql
                , new BeanPropertyRowMapper(PassRecordVehicle.class)
                , "出");
        List resultss = jdbcTemplate.query(sql
                , new BeanPropertyRowMapper<>(PassRecordVehicle.class)
                ,"进");
        boolean empty = CollectionUtils.isEmpty(resultss);
        Assert.isTrue(!empty , "PassRecordVehicle查询结果不能为空");
        PassRecordVehicle passRecordVehicle = (PassRecordVehicle) resultss.get(0);
        results.add(passRecordVehicle);
        return results;
    }

    /**
     * 大屏页面四
     * @return
     */
    public Map<String, PassRecordVehicle> getVehicleTypeAndBayonetFlow() {

        String sql = "SELECT sum(HongQi_Road) HongQi_Road," +
                "sum(HaiGang_South_Road) HaiGang_South_Road," +
                "sum(HaiGang_North_Road) HaiGang_North_Road," +
                "sum(NanTi_Road) NanTi_Road," +
                "sum(HaiFang_North_Road) HaiFang_North_Road," +
                "sum(HaiFang_Middle_Road) HaiFang_Middle_Road," +
                "sum(HaiFang_South_Road) HaiFang_South_Road," +
                "sum(ChuangYe_Road) ChuangYe_Road," +
                "sum(BinHai_North_Road) BinHai_North_Road " +
                "FROM ng_pass_record_vehicle " +
                "where VEHICLE_TYPE = ?";
        String[] vehicleTypeArray =
                {"普通货车","普通客车","危化品车辆","施工车辆","抢险救援车辆","未知车辆"};
        //车辆类型做key，PassRecordVehicle做value
        Map<String, PassRecordVehicle> resultMap = new HashMap<>();
        for (String vehicleType : vehicleTypeArray) {
            List<PassRecordVehicle> result = jdbcTemplate.query(sql
                    , new BeanPropertyRowMapper(PassRecordVehicle.class)
                    , vehicleType);
            if(!CollectionUtils.isEmpty(result)){
                resultMap.put(vehicleType , result.get(0));
            }
        }
        if(null != resultMap && resultMap.containsKey(NangangGlobal.OTHER_VIHICLE)){
            if(resultMap.containsKey(NangangGlobal.SHIGONG_VIHICLE)){
                PassRecordVehicle otherVehicle = resultMap.get(NangangGlobal.OTHER_VIHICLE);
                PassRecordVehicle shigongVehicle = resultMap.get(NangangGlobal.SHIGONG_VIHICLE);
                String shigongVehicleRoad = null;
                String otherVehicleRoad = null;
                if(null != otherVehicle && null != shigongVehicle){
                    //红旗路
                    otherVehicleRoad = otherVehicle.getHongqiRoad();
                    shigongVehicleRoad = shigongVehicle.getHongqiRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setHongqiRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //海防南路
                    otherVehicleRoad = otherVehicle.getHaifangSouthRoad();
                    shigongVehicleRoad = shigongVehicle.getHaifangSouthRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setHaifangSouthRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //海防北路
                    otherVehicleRoad = otherVehicle.getHaifangNorthRoad();
                    shigongVehicleRoad = shigongVehicle.getHaifangNorthRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setHaifangNorthRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //海防中路
                    otherVehicleRoad = otherVehicle.getHaifangMiddleRoad();
                    shigongVehicleRoad = shigongVehicle.getHaifangMiddleRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setHaifangMiddleRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //海港南路
                    otherVehicleRoad = otherVehicle.getHaigangSouthRoad();
                    shigongVehicleRoad = shigongVehicle.getHaigangSouthRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setHaigangSouthRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //海港北路
                    otherVehicleRoad = otherVehicle.getHaigangNorthRoad();
                    shigongVehicleRoad = shigongVehicle.getHaigangNorthRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setHaigangNorthRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //南堤路
                    otherVehicleRoad = otherVehicle.getNantiRoad();
                    shigongVehicleRoad = shigongVehicle.getNantiRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setNantiRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //创业路
                    otherVehicleRoad = otherVehicle.getChuangyeRoad();
                    shigongVehicleRoad = shigongVehicle.getChuangyeRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setChuangyeRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                    //滨海北路
                    otherVehicleRoad = otherVehicle.getBinhaiNorthRoad();
                    shigongVehicleRoad = shigongVehicle.getBinhaiNorthRoad();
                    if(null != otherVehicleRoad && null != shigongVehicleRoad){
                        shigongVehicle.setBinhaiNorthRoad(String.valueOf(Integer.parseInt(otherVehicleRoad)
                                + Integer.parseInt(shigongVehicleRoad)));
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 大屏页面三
     * @return
     */
    public List<PassRecordVehicle> getBayonetFlowProportion() {
        QPassRecordVehicle passRecordVehicle = QPassRecordVehicle.passRecordVehicle;
        JPQLQuery<PassRecordVehicle> query = from(passRecordVehicle);
        BooleanBuilder builder = new BooleanBuilder();
        query.select(Projections.bean(
                PassRecordVehicle.class
                ,passRecordVehicle.hongqiRoad
                ,passRecordVehicle.haigangSouthRoad
                ,passRecordVehicle.haigangNorthRoad
                ,passRecordVehicle.nantiRoad
                ,passRecordVehicle.haifangNorthRoad
                ,passRecordVehicle.haifangMiddleRoad
                ,passRecordVehicle.haifangSouthRoad
                ,passRecordVehicle.chuangyeRoad
                ,passRecordVehicle.binhaiNorthRoad
                ,passRecordVehicle.totalVehicle
        )).where(builder.and(passRecordVehicle.vehicleType.eq("总量")));
        return findAll(query);
    }

    /**
     * 危化品车辆在企业的分布
     * @return
     */
//    SELECT Name, a.total FROM	IF_STAT_COMPANY
//    LEFT JOIN ( SELECT v.COMPANY_ID,	sum(count) total FROM IF_STAT_VEHICLE v
//    WHERE( v.START_TIME BETWEEN TO_date( '2019-02-26 00:00:00', 'yyyy-mm-dd hh24:mi:ss' )
//    AND TO_date( '2019-02-26 23:59:59', 'yyyy-mm-dd hh24:mi:ss' )
//    AND in_out_flag = 1 AND vehicle_type = 3 ) GROUP BY v.COMPANY_ID
//	) a ON ID = a.COMPANY_ID WHERE total >= 1 ORDER BY total DESC;

    public List<VehicleNumberOfCompanyVo> getVehicleByCompany() {
        LocalDateTime time = utilsRepository.now();
        String nowTime = DateUtil.getDateStr(time);
        String startDate = nowTime + " 00:00:00";
        String endDate = nowTime + " 23:59:59";
        StringBuffer sql = new StringBuffer("SELECT Name, a.total FROM IF_STAT_COMPANY " )
                .append( "LEFT JOIN ( SELECT v.COMPANY_ID, sum(count) total FROM IF_STAT_VEHICLE v WHERE( v.START_TIME BETWEEN TO_date('")
                 .append(startDate).append("', 'yyyy-mm-dd hh24:mi:ss' ) AND TO_date('").append(endDate)
                .append("', 'yyyy-mm-dd hh24:mi:ss' ) AND in_out_flag = 1 AND vehicle_type = 3 ) GROUP BY v.COMPANY_ID ")
                .append(") a ON ID = a.COMPANY_ID WHERE total >= 1 ORDER BY total DESC");

        List query = jdbcTemplate.query(sql.toString()
                , new BeanPropertyRowMapper(VehicleNumberOfCompanyVo.class)
        );
        return query;
    }
}
