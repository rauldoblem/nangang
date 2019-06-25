package com.taiji.emp.zn.repository;

import com.taiji.base.common.utils.SqlFormat;
import com.taiji.emp.zn.vo.DateStatVo;
import com.taiji.emp.zn.vo.EventAndAlertDateStatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DateStatRepository {

    @Value("${spring.datasource.platform}")
    private String platform;

    @Autowired
    SqlFormat sqlFormat;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //按时间统计突发事件(事发时间--occurTime)总数和预警(创建时间--createTime)总数
    public EventAndAlertDateStatVo statEventAndAlertDate(MultiValueMap<String, Object> params){
        List<Object> statDate = params.get("statDate");
        List<Object> alertStatus = params.get("alertStatus");
        Map<String,Integer> eventStatMap = statEventDate(statDate);
        Map<String,Integer> statAlertDate = statAlertDate(statDate,alertStatus);
        return new EventAndAlertDateStatVo(eventStatMap,statAlertDate);
    }

    //事件信息统计
    private Map<String,Integer> statEventDate(List<Object> statDate){
        Map<String,Integer> map = new HashMap<>();

        //拼接 in('2018-10','2018-11','2018-12',...)
        String statDateStr = "";
        if(!CollectionUtils.isEmpty(statDate)){
            StringBuilder statDateBuilder = new StringBuilder();
            statDateBuilder.append("(");
            for(Object date : statDate){
                statDateBuilder.append("'").append((String) date).append("'").append(",");
            }
            statDateStr = statDateBuilder.toString();
            statDateStr = statDateStr.substring(0,statDateStr.length()-1);
            statDateStr+=")";
        }

        String dateFormatStr = "";
        if("mysql".equals(platform)){ //mysql环境
            dateFormatStr = " date_format(t.OCCUR_TIME, '%Y-%m')";
        }else{//oracle环境或postgre环境
            dateFormatStr = " to_char(t.OCCUR_TIME, 'YYYY-MM')";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("select").append(dateFormatStr).append(" as date_str,count(t.OCCUR_TIME) as num")
                .append(" from ec_event t");
        if(!"".equals(statDateStr)){
            builder.append(" where").append(dateFormatStr).append(" in ").append(statDateStr);
        }
        builder.append(" group by").append(dateFormatStr)
                .append(" ORDER BY").append(dateFormatStr).append(" asc");

        String sql = sqlFormat.sqlFormat(builder.toString());
        List<DateStatVo> list = jdbcTemplate.query(sql ,new BeanPropertyRowMapper(DateStatVo.class));
        map = list.stream().collect(Collectors.toMap(temp -> temp.getDateStr(), temp ->temp.getNum()));
        return map;
    }

    //预警信息统计
    private Map<String,Integer> statAlertDate(List<Object> statDate,List<Object> alertStatus){
        Map<String,Integer> map = new HashMap<>();

        //拼接 in('2018-10','2018-11','2018-12',...)
        String statDateStr = "";
        if(!CollectionUtils.isEmpty(statDate)){
            StringBuilder statDateBuilder = new StringBuilder();
            statDateBuilder.append("(");
            for(Object date : statDate){
                statDateBuilder.append("'").append((String) date).append("'").append(",");
            }
            statDateStr = statDateBuilder.toString();
            statDateStr = statDateStr.substring(0,statDateStr.length()-1);
            statDateStr+=")";
        }

        //拼接 in('2','3')
        String alertStatusStr = "";
        if(!CollectionUtils.isEmpty(alertStatus)){
            StringBuilder alertStatusBuilder = new StringBuilder();
            alertStatusBuilder.append("(");
            for(Object status : alertStatus){
                alertStatusBuilder.append("'").append((String) status).append("'").append(",");
            }
            alertStatusStr = alertStatusBuilder.toString();
            alertStatusStr = alertStatusStr.substring(0,alertStatusStr.length()-1);
            alertStatusStr+=")";
        }

        String dateFormatStr = "";
        if("mysql".equals(platform)){ //mysql环境
            dateFormatStr = " date_format(t.CREATE_TIME, '%Y-%m')";
        }else{//oracle环境或postgre环境
            dateFormatStr = " to_char(t.CREATE_TIME, 'YYYY-MM')";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("select").append(dateFormatStr).append(" as date_str,count(t.CREATE_TIME) as num")
                .append(" from ma_alert t");
                if(!"".equals(statDateStr)){
                    builder.append(" where").append(dateFormatStr).append(" in ").append(statDateStr);
                }
                if(!"".equals(statDateStr)){
                    builder.append(" and t.ALERT_STATUS in ").append(alertStatusStr);
                }
        builder.append(" group by").append(dateFormatStr)
                .append(" ORDER BY").append(dateFormatStr).append(" asc");

        String sql = sqlFormat.sqlFormat(builder.toString());
        List<DateStatVo> list = jdbcTemplate.query(sql ,new BeanPropertyRowMapper(DateStatVo.class));
        map = list.stream().collect(Collectors.toMap(temp -> temp.getDateStr(), temp ->temp.getNum()));
        return map;
    }

}
