package com.taiji.emp.zn.repository;

import com.taiji.base.common.utils.SqlFormat;
import com.taiji.emp.zn.vo.EventTypeStatVo;
import com.taiji.emp.zn.vo.TargetTypeStatVo;
import com.taiji.emp.zn.vo.TeamTypeStatVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResStatRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SqlFormat sqlFormat;
    /**
     * 统计重点防护目标各大类数量 -- 目标类型
     */
    public List<TargetTypeStatVo> statTargetByType(){
        StringBuilder builder = new StringBuilder();
        builder.append("select p.target_type_id,s.item_name as target_type_name,p.total_num ")
                .append(" from")
                .append(" (SELECT t.target_type_id,COUNT(t.target_type_id) as total_num from ")
                .append(" (SELECT * from rc_target where del_flag = '").append(DelFlagEnum.NORMAL.getCode()).append("') t")
                .append(" GROUP BY (t.target_type_id)")
                .append(" ) p")
                .append(" LEFT JOIN")
                .append(" sys_dic_group_items s")
                .append(" on p.target_type_id = s.id");
        //根据数据源选择格式化原生sql的大小写
        String sql = sqlFormat.sqlFormat(builder.toString());
        List<TargetTypeStatVo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(TargetTypeStatVo.class));
        return list;
    }

    /**
     * 统计应急专家各大类数量 -- 事件类型
     */
    public List<EventTypeStatVo> statExpertByType(){
        StringBuilder builder = new StringBuilder();
        builder.append("select i.event_type_id,d.item_name as event_type_name,i.total_num from")
                .append(" (")
                .append(" select r.item_id as event_type_id,COUNT(r.item_id) as total_num from")
                .append(" (")
                .append(" select s.id as item_id,s.item_name from")
                .append(" (select * from sys_dic_group_items where dic_code = 'dicEventType' and del_flag = '").append(DelFlagEnum.NORMAL.getCode()).append("') s")
                .append(" inner join")
                .append(" (select * from ER_EXPERT where del_flag = '").append(DelFlagEnum.NORMAL.getCode()).append("') t")
                .append(" on t.event_type_ids like concat(concat('%',s.id),'%')")
                .append(" ) r")
                .append(" GROUP BY r.item_id")
                .append(" ) i")
                .append(" left join")
                .append(" sys_dic_group_items d")
                .append(" on i.event_type_id = d.id");

        //根据数据源选择格式化原生sql的大小写
        String sql = sqlFormat.sqlFormat(builder.toString());
        List<EventTypeStatVo> list = jdbcTemplate.query(sql ,new BeanPropertyRowMapper(EventTypeStatVo.class));
        return list;
    }

    /**
     * 统计应急队伍各大类数量 -- 队伍类型
     */
    public List<TeamTypeStatVo> statTeamByType(){
        StringBuilder builder = new StringBuilder();
        builder.append("select p.team_type_id,s.item_name as team_type_name,p.total_num")
                .append(" from")
                .append(" (SELECT t.team_type_id,COUNT(t.team_type_id) as total_num from")
                .append(" (SELECT * from ER_TEAM where DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("') t")
                .append(" GROUP BY (t.team_type_id)")
                .append(" ) p")
                .append(" LEFT JOIN")
                .append(" sys_dic_group_items s")
                .append(" on p.team_type_id = s.id");
        //根据数据源选择格式化原生sql的大小写
        String sql = sqlFormat.sqlFormat(builder.toString());
        List<TeamTypeStatVo> list = jdbcTemplate.query(sql ,new BeanPropertyRowMapper(TeamTypeStatVo.class));
        return list;
    }

}
