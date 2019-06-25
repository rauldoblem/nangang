//package com.taiji.emp.nangang.repository;
//
//import com.taiji.emp.nangang.entity.PassRecordVehicle;
//import com.taiji.emp.nangang.entity.Weather;
//import com.taiji.micro.common.repository.UtilsRepository;
//import com.taiji.micro.common.utils.DateUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author yhcookie
// * @date 2018/12/5 10:55
// */
//@Repository
//public class StatVehicleRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Value("${spring.datasource.platform}")
//    private String platform;
//    @Autowired
//    UtilsRepository utilsRepository;
//
//    /**
//     * 从封闭系统触发过来表IF_STAT_VEHICLE中查询需要的数据
//     * 结果集封装成PassRecordVehicle返回
//     * @return List<PassRecordVehicle>
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public List<PassRecordVehicle> findVehicleRecord()
//    {
//        String sql = "";
//        if( "mysql".equals(platform)){
//            String sqlForMysql = "SELECT " +
//                    "tt.*, " +
//                    "tt.BINHAI_NORTH_ROAD + tt.CHUANGYE_ROAD + tt.HAIFANG_MIDDLE_ROAD + tt.HAIFANG_NORTH_ROAD " +
//                    "+ tt.HAIFANG_SOUTH_ROAD + tt.HAIGANG_NORTH_ROAD + tt.HAIGANG_SOUTH_ROAD + tt.HONGQI_ROAD + " +
//                    "tt.NANTI_ROAD " +
//                    "as total_vehicle " +
//                    "" +
//                    "FROM " +
//                    "( " +
//                    "SELECT " +
//                    "case t.VEHICLE_TYPE when 1 then '普通客车' when 2 then '普通货车' when 3 then '危化品车辆' when 4 then '施工车辆' when 5 then '抢险救援特种车辆' else '未知车辆' END VEHICLE_TYPE, " +
//                    "case t.IN_OUT_FLAG when 0 then '进' when 1 then '出' END IN_OUT_FLAG, " +
//                    "SUM(case t.BAYONET_ID when 1 then t.pass_count else 0 END) HongQi_Road, " +
//                    "SUM(case t.BAYONET_ID when 2 then t.pass_count else 0 END) HaiGang_South_Road, " +
//                    "SUM(case t.BAYONET_ID when 3 then t.pass_count else 0 END) HaiGang_North_Road, " +
//                    "SUM(case t.BAYONET_ID when 4 then t.pass_count else 0 END) NanTi_Road, " +
//                    "SUM(case t.BAYONET_ID when 5 then t.pass_count else 0 END) HaiFang_North_Road, " +
//                    "SUM(case t.BAYONET_ID when 6 then t.pass_count else 0 END) HaiFang_Middle_Road, " +
//                    "SUM(case t.BAYONET_ID when 7 then t.pass_count else 0 END) HaiFang_South_Road, " +
//                    "SUM(case t.BAYONET_ID when 8 then t.pass_count else 0 END) ChuangYe_Road, " +
//                    "SUM(case t.BAYONET_ID when 9 then t.pass_count else 0 END) BinHai_North_Road, " +
//                    "t.START_DATE " +
//                    "FROM " +
//                    "( SELECT " +
//                    "date_format(START_TIME, '%Y-%m-%d') start_date,BAYONET_ID,sum(count) pass_count, " +
//                    "vehicle_type," +
//                    "IN_OUT_FLAG " +
//                    "FROM " +
//                    "IF_STAT_VEHICLE " +
//                    "WHERE " +
//                    "BAYONET_id is not null " +
//                    "AND " +
//                    "(START_TIME BETWEEN str_to_date(?, '%Y-%m-%d %H:%i:%s') " +
//                    "AND " +
//                    "str_to_date(?,  '%Y-%m-%d %H:%i:%s')) " +
//                    "GROUP BY " +
//                    "date_format(START_TIME, '%Y-%m-%d'),BAYONET_ID,vehicle_type,IN_OUT_FLAG " +
//                    ") t " +
//                    "" +
//                    "WHERE " +
//                    "t.BAYONET_ID IS NOT NULL " +
//                    "GROUP BY " +
//                    "VEHICLE_TYPE, t.START_DATE, t.IN_OUT_FLAG " +
//                    "UNION ALL " +
//                    "SELECT " +
//                    "'总量' as VEHICLE_TYPE, " +
//                    "null as IN_OUT_FLAG, " +
//                    "SUM(case t.BAYONET_ID when 1 then t.pass_count else 0 END) HongQi_Road, " +
//                    "SUM(case t.BAYONET_ID when 2 then t.pass_count else 0 END) HaiGang_South_Road, " +
//                    "SUM(case t.BAYONET_ID when 3 then t.pass_count else 0 END) HaiGang_North_Road, " +
//                    "SUM(case t.BAYONET_ID when 4 then t.pass_count else 0 END) NanTi_Road, " +
//                    "SUM(case t.BAYONET_ID when 5 then t.pass_count else 0 END) HaiFang_North_Road, " +
//                    "SUM(case t.BAYONET_ID when 6 then t.pass_count else 0 END) HaiFang_Middle_Road, " +
//                    "SUM(case t.BAYONET_ID when 7 then t.pass_count else 0 END) HaiFang_South_Road, " +
//                    "SUM(case t.BAYONET_ID when 8 then t.pass_count else 0 END) ChuangYe_Road, " +
//                    "SUM(case t.BAYONET_ID when 9 then t.pass_count else 0 END) BinHai_North_Road," +
//                    "null as START_DATE " +
//                    "FROM " +
//                    "( " +
//                    "SELECT " +
//                    "date_format(START_TIME, '%Y-%m-%d') start_date," +
//                    "BAYONET_ID," +
//                    "sum(count) pass_count," +
//                    "vehicle_type," +
//                    "IN_OUT_FLAG " +
//                    "FROM " +
//                    "IF_STAT_VEHICLE " +
//                    "WHERE " +
//                    "BAYONET_id is not null " +
//                    "AND " +
//                    "(" +
//                    "START_TIME " +
//                    "BETWEEN " +
//                    "str_to_date(?,  '%Y-%m-%d %H:%i:%s') " +
//                    "AND " +
//                    "str_to_date(?,  '%Y-%m-%d %H:%i:%s') " +
//                    ")" +
//                    "GROUP BY " +
//                    "date_format(START_TIME, '%Y-%m-%d'),BAYONET_ID,vehicle_type,IN_OUT_FLAG " +
//                    ") t " +
//                    "WHERE " +
//                    "t.BAYONET_ID IS NOT NULL " +
//                    ") tt " +
//                    "ORDER BY " +
//                    "tt.START_DATE, tt.VEHICLE_TYPE , tt.IN_OUT_FLAG " +
//                    "desc";
//            sql = sqlForMysql;
//        }else if("oracle".equals(platform)){
//            String sqlForOracle = "SELECT " +
//                    "tt.*, " +
//                    "tt.BINHAI_NORTH_ROAD + tt.CHUANGYE_ROAD + tt.HAIFANG_MIDDLE_ROAD + tt.HAIFANG_NORTH_ROAD " +
//                    "+ tt.HAIFANG_SOUTH_ROAD + tt.HAIGANG_NORTH_ROAD + tt.HAIGANG_SOUTH_ROAD + tt.HONGQI_ROAD + " +
//                    "tt.NANTI_ROAD " +
//                    "as total_vehicle " +
//                    "" +
//                    "FROM " +
//                    "( " +
//                    "SELECT " +
//                    "DECODE (t.VEHICLE_TYPE, 1, '普通客车', 2, '普通货车', 3, '危化品车辆', 4, '施工车辆', 5, '抢险救援特种车辆', '未知车辆') VEHICLE_TYPE, " +
//                    "DECODE (t.IN_OUT_FLAG, 0, '进', 1, '出') IN_OUT_FLAG, " +
//                    "SUM(DECODE(t.BAYONET_ID, 1, t.pass_count, 0)) HongQi_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 2, t.pass_count, 0)) HaiGang_South_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 3, t.pass_count, 0)) HaiGang_North_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 4, t.pass_count, 0)) NanTi_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 5, t.pass_count, 0)) HaiFang_North_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 6, t.pass_count, 0)) HaiFang_Middle_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 7, t.pass_count, 0)) HaiFang_South_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 8, t.pass_count, 0)) ChuangYe_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 9, t.pass_count, 0)) BinHai_North_Road, " +
//                    "t.START_DATE " +
//                    "FROM " +
//                    "( SELECT " +
//                    "TO_char (START_TIME, 'yyyy-mm-dd') start_date,BAYONET_ID,sum(count) pass_count, " +
//                    "vehicle_type," +
//                    "IN_OUT_FLAG " +
//                    "FROM " +
//                    "IF_STAT_VEHICLE " +
//                    "WHERE " +
//                    "BAYONET_id is not null " +
//                    "AND " +
//                    "(START_TIME BETWEEN TO_date ( ?, 'yyyy-mm-dd hh24:mi:ss') " +
//                    "AND " +
//                    "TO_date ( ?, 'yyyy-mm-dd hh24:mi:ss')) " +
//                    "GROUP BY " +
//                    "TO_char (START_TIME, 'yyyy-mm-dd'),BAYONET_ID,vehicle_type,IN_OUT_FLAG " +
//                    ") t " +
//                    "" +
//                    "WHERE " +
//                    "t.BAYONET_ID IS NOT NULL " +
//                    "GROUP BY " +
//                    "VEHICLE_TYPE, t.START_DATE, t.IN_OUT_FLAG " +
//                    "" +
//                    "UNION ALL " +
//                    "" +
//                    "SELECT " +
//                    "'总量' as VEHICLE_TYPE, " +
//                    "null as IN_OUT_FLAG, " +
//                    "SUM(DECODE(t.BAYONET_ID, 1, t.pass_count, 0)) HongQi_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 2, t.pass_count, 0)) HaiGang_South_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 3, t.pass_count, 0)) HaiGang_North_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 4, t.pass_count, 0)) NanTi_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 5, t.pass_count, 0)) HaiFang_North_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 6, t.pass_count, 0)) HaiFang_Middle_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 7, t.pass_count, 0)) HaiFang_South_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 8, t.pass_count, 0)) ChuangYe_Road, " +
//                    "SUM(DECODE(t.BAYONET_ID, 9, t.pass_count, 0)) BinHai_North_Road," +
//                    "null as START_DATE " +
//                    "FROM " +
//                    "( " +
//                    "SELECT " +
//                    "TO_char (START_TIME, 'yyyy-mm-dd') start_date," +
//                    "BAYONET_ID," +
//                    "sum(count) pass_count," +
//                    "vehicle_type," +
//                    "IN_OUT_FLAG " +
//                    "FROM " +
//                    "IF_STAT_VEHICLE " +
//                    "WHERE " +
//                    "BAYONET_id is not null " +
//                    "AND " +
//                    "(" +
//                    "START_TIME " +
//                    "BETWEEN " +
//                    "TO_date ( ?, 'yyyy-mm-dd hh24:mi:ss') " +
//                    "AND " +
//                    "TO_date ( ?, 'yyyy-mm-dd hh24:mi:ss') " +
//                    ")" +
//                    "GROUP BY " +
//                    "TO_char (START_TIME, 'yyyy-mm-dd'),BAYONET_ID,vehicle_type,IN_OUT_FLAG " +
//                    ") t " +
//                    "WHERE " +
//                    "t.BAYONET_ID IS NOT NULL " +
//                    ") tt " +
//                    "ORDER BY " +
//                    "tt.START_DATE, tt.VEHICLE_TYPE , tt.IN_OUT_FLAG " +
//                    "desc";
//            sql = sqlForOracle;
//        }
//        boolean empty = StringUtils.isEmpty(sql);
//        Assert.isTrue(!empty,"sql不能为空，数据源应为mysql或者oracle");
//        //时间写当天（数据库时间）
//        LocalDateTime time = utilsRepository.now();
//        String nowTime = DateUtil.getDateStr(time);
//        String startDate = nowTime + " 00:00:00";
//        String endDate = nowTime + " 23:59:59";
//
//
//        //创建query并指定结果集类型，设置sql参数
//        List resultList =
//                jdbcTemplate.query(sql
//                        ,new BeanPropertyRowMapper(PassRecordVehicle.class)
//                        ,startDate
//                        ,endDate
//                        ,startDate
//                        ,endDate);
//
//        return resultList;
//    }
//
//}
