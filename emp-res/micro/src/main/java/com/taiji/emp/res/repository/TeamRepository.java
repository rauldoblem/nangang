package com.taiji.emp.res.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.common.utils.SqlFormat;
import com.taiji.emp.res.entity.QTeam;
import com.taiji.emp.res.entity.Team;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.searchVo.team.TeamPageVo;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class TeamRepository extends BaseJpaRepository<Team,String> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    SqlFormat sqlFormat;

    public Page<Team> findPage(TeamPageVo teamPageVo, Pageable pageable){
        QTeam team = QTeam.team;
        JPQLQuery<Team> query = from(team);

        BooleanBuilder builder = new BooleanBuilder();

        String teamName = teamPageVo.getTeamName();
        List<String> teamTypeIds = teamPageVo.getTeamTypeIds();
        String teamPropertyId = teamPageVo.getTeamPropertyId();
        String teamUnit = teamPageVo.getUnit();
        String teamSpecialty = teamPageVo.getTeamSpecialty();
        String createOrgId =teamPageVo.getCreateOrgId();
        List<String> teamIds = teamPageVo.getTeamIds();
        //已选队伍IDS，在结果列表中，将这些队伍去掉（预案数字化时使用）
        List<String> selectedTeamIds = teamPageVo.getSelectedTeamIds();

        if(StringUtils.hasText(teamName)){
            builder.and(team.name.contains(teamName));
        }
        if(StringUtils.hasText(teamPropertyId)){
            builder.and(team.propertyId.eq(teamPropertyId));
        }
        if(null!=teamTypeIds&&teamTypeIds.size()>0){
            builder.and(team.teamTypeId.in(teamTypeIds));
        }
        //预案数字化查询相应的队伍信息
        if(null!=teamIds&&teamIds.size()>0){
            builder.and(team.id.in(teamIds));
        }
        if(StringUtils.hasText(teamUnit)){
            builder.and(team.unit.contains(teamUnit));
        }
        if(StringUtils.hasText(teamSpecialty)){
            builder.and(team.specialty.contains(teamSpecialty));
        }
        if(StringUtils.hasText(createOrgId)){
            builder.and(team.createOrgId.eq(createOrgId));
        }
        if(null!=selectedTeamIds&&selectedTeamIds.size()>0){
            builder.and(team.id.notIn(selectedTeamIds));
        }
        builder.and(team.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Team.class
                        ,team.id
                        ,team.name
                        ,team.propertyId
                        ,team.propertyName
                        ,team.teamTypeId
                        ,team.teamTypeName
                        ,team.unit
                        ,team.dutyTel
                        ,team.principal
                        ,team.principalTel
                        ,team.teamNum
                        ,team.lonAndLat
                        ,team.address
                        ,team.specialty
                        ,team.constituted
                        ,team.materials
                        ,team.teamAbility
                        ,team.teamCase
                        ,team.notes
                        ,team.createOrgId
                )).where(builder)
                .orderBy(team.updateTime.desc());

        return findAll(query,pageable);
    }

    public List<Team> findList(TeamListVo teamListVo){
        QTeam team = QTeam.team;
        JPQLQuery<Team> query = from(team);

        BooleanBuilder builder = new BooleanBuilder();

        String teamName = teamListVo.getTeamName();
        List<String> teamTypeIds = teamListVo.getTeamTypeIds();
        String teamPropertyId = teamListVo.getTeamPropertyId();
        String teamUnit = teamListVo.getUnit();
        String teamSpecialty = teamListVo.getTeamSpecialty();
        String createOrgId =teamListVo.getCreateOrgId();
        List<String> teamIds = teamListVo.getTeamIds();
        //已选队伍IDS，在结果列表中，将这些队伍去掉（预案数字化时使用）
        List<String> selectedTeamIds = teamListVo.getSelectedTeamIds();

        if(StringUtils.hasText(teamName)){
            builder.and(team.name.contains(teamName));
        }
        if(StringUtils.hasText(teamPropertyId)){
            builder.and(team.propertyId.eq(teamPropertyId));
        }
        if(null!=teamTypeIds&&teamTypeIds.size()>0){
            builder.and(team.teamTypeId.in(teamTypeIds));
        }
        if(null!=teamIds&&teamIds.size()>0){
            builder.and(team.id.in(teamIds));
        }
        if(StringUtils.hasText(teamUnit)){
            builder.and(team.unit.contains(teamUnit));
        }
        if(StringUtils.hasText(teamSpecialty)){
            builder.and(team.specialty.contains(teamSpecialty));
        }
        if(StringUtils.hasText(createOrgId)){
            builder.and(team.createOrgId.eq(createOrgId));
        }
        if(null!=selectedTeamIds&&selectedTeamIds.size()>0){
            builder.and(team.id.notIn(selectedTeamIds));
        }
        builder.and(team.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Team.class
                        ,team.id
                        ,team.name
                        ,team.propertyId
                        ,team.propertyName
                        ,team.teamTypeId
                        ,team.teamTypeName
                        ,team.unit
                        ,team.dutyTel
                        ,team.principal
                        ,team.principalTel
                        ,team.teamNum
                        ,team.lonAndLat
                        ,team.address
                        ,team.specialty
                        ,team.constituted
                        ,team.materials
                        ,team.teamAbility
                        ,team.teamCase
                        ,team.notes
                        ,team.createOrgId
                )).where(builder)
                .orderBy(team.updateTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public Team save(Team entity){

        Assert.notNull(entity,"Team 对象不能为 null");

        Team result;

        if(StringUtils.isEmpty(entity.getId())){ //新增保存
            result = super.save(entity);
        }else { //编辑保存
            Team team = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,team);
            result = super.save(team);
        }

        return result;

    }

    /**
     * 通过schemeId救援队伍信息
     * @param schemeId
     * @return
     */
    public List<TeamVo> findBySchemeId(String schemeId) {
        int builderLength = 350;
        StringBuilder builder = new StringBuilder(builderLength);
        builder.append("SELECT * FROM ER_TEAM WHERE ID IN ")
                .append("(SELECT TEAM_ID FROM EC_TEAM WHERE SCHEME_ID = '")
                .append(schemeId).append("' AND DEL_FLAG = '").append(DelFlagEnum.NORMAL.getCode()).append("')");
        String sql = sqlFormat.sqlFormat(builder.toString());
        return jdbcTemplate.query(sql , new BeanPropertyRowMapper(TeamVo.class));
    }
}
