package com.taiji.emp.res.service;

import com.taiji.emp.res.feign.PlanTeamClient;
import com.taiji.emp.res.feign.TeamClient;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamListVo;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamPageVo;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.searchVo.team.TeamPageVo;
import com.taiji.emp.res.vo.PlanTeamVo;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlanTeamService extends BaseService{

    @Autowired
    private PlanTeamClient planTeamClient;

    @Autowired
    private TeamClient teamClient;

    //新增预案团队管理
    public void create(PlanTeamVo vo, Principal principal){

//        String eventGradeID = vo.getEventGradeID();
//        Assert.hasText(eventGradeID,"eventGradeID 不能为空");
//        vo.setEventGradeName(getItemNamesByIds(eventGradeID));

        ResponseEntity<PlanTeamVo> resultVo = planTeamClient.create(vo);

    }

    //修改预案团队管理
    public void update(PlanTeamVo vo, Principal principal,String id){

//        String eventGradeId = vo.getEventGradeId();
//        Assert.hasText(eventGradeId,"eventGradeID 不能为空");
//        PlanTeamVo tempVo = findOne(vo.getId());
//        if(!eventGradeId.equals(tempVo.getEventGradeId())){ //有修改才更新name
//            vo.setEventGradeName(getItemNamesByIds(eventGradeId));
//        }else{
//            vo.setEventGradeName(null);
//        }

        ResponseEntity<PlanTeamVo> resultVo = planTeamClient.update(vo,id);

    }

    /**
     * 根据id获取单条预案团队管理
     */
    public PlanTeamVo findOne(String id){
        Assert.hasText(id,"id不能为空字符串");
        ResponseEntity<PlanTeamVo> resultVo = planTeamClient.findOne(id);
        PlanTeamVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }

    public void deletePhysical(PlanTeamVo vo){
        Assert.notNull(vo,"PlanTeamVo不能为 null");
        ResponseEntity<Void> resultVo = planTeamClient.deletePhysical(vo);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 获取应急团队管理分页list
     */
    public RestPageImpl<TeamVo> findPage(PlanTeamPageVo planTeamPageVo){
        Assert.notNull(planTeamPageVo,"PlanTeamPageVo 不能为空");
        PlanTeamListVo vo = new PlanTeamListVo();
        vo.setEventGradeId(planTeamPageVo.getEventGradeId());
        vo.setPlanId(planTeamPageVo.getPlanId());
        TeamPageVo teamPageVo = new TeamPageVo();
        teamPageVo.setPage(planTeamPageVo.getPage());
        teamPageVo.setSize(planTeamPageVo.getSize());
        ResponseEntity<List<PlanTeamVo>> planTeamVo = planTeamClient.findList(vo);
        List<PlanTeamVo> planTeam = ResponseEntityUtils.achieveResponseEntityBody(planTeamVo);
        if (!CollectionUtils.isEmpty(planTeam)){
            List<String> str = new ArrayList<String>();
            for (PlanTeamVo pt: planTeam) {
                str.add(pt.getTeamId());
            }
            teamPageVo.setTeamIds(str);
            ResponseEntity<RestPageImpl<TeamVo>> resultVo = teamClient.findPage(teamPageVo);
            RestPageImpl<TeamVo> teamVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            return teamVo;
        }else{
            RestPageImpl<TeamVo> teamVos = new RestPageImpl<TeamVo>();
            return teamVos;
        }
    }

    /**
     * 获取预案团队管理list(不带分页)
     */
    public List<TeamVo> findList(PlanTeamListVo planTeamListVo){
        Assert.notNull(planTeamListVo,"PlanTeamListVo 不能为空");
        TeamListVo teamListVo = new TeamListVo();
        ResponseEntity<List<PlanTeamVo>> planTeamVo = planTeamClient.findList(planTeamListVo);
        List<PlanTeamVo> planTeam = ResponseEntityUtils.achieveResponseEntityBody(planTeamVo);
        if (!CollectionUtils.isEmpty(planTeam)){
            List<String> str = new ArrayList<String>();
            for (PlanTeamVo pt: planTeam) {
                str.add(pt.getTeamId());
            }
            teamListVo.setTeamIds(str);
            ResponseEntity<List<TeamVo>> resultVo = teamClient.findList(teamListVo);
            List<TeamVo>  teamVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
            return teamVo;
        }else{
            List<TeamVo> teamVos = new ArrayList<TeamVo>();
            return teamVos;
        }

    }

    /**
     * 获取预案团队管理list(不带分页)去重
     */
    public List<TeamVo> findTeamsByPlanIds(PlanTeamListVo planTeamListVo){
        Assert.notNull(planTeamListVo,"PlanTeamListVo 不能为空");
        TeamListVo teamListVo = new TeamListVo();
        ResponseEntity<List<PlanTeamVo>> planTeamVo = planTeamClient.findList(planTeamListVo);
        List<PlanTeamVo> planTeam = ResponseEntityUtils.achieveResponseEntityBody(planTeamVo);
        List<String> str = new ArrayList<String>();
        for (PlanTeamVo pt: planTeam) {
            str.add(pt.getTeamId());
        }
        Set h = new HashSet(str);
        str.clear();
        str.addAll(h);
        teamListVo.setTeamIds(str);
        ResponseEntity<List<TeamVo>> resultVo = teamClient.findList(teamListVo);
        List<TeamVo>  teamVo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return teamVo;
    }

}
