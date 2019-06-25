package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.PlanTeam;
import com.taiji.emp.res.entity.Team;
import com.taiji.emp.res.repository.PlanTeamRepository;
import com.taiji.emp.res.repository.TeamRepository;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamListVo;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamPageVo;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.vo.PlanTeamVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlanTeamService extends BaseService<PlanTeam,String>{

    @Autowired
    private PlanTeamRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    public PlanTeam createOrUpdate(PlanTeamVo vo){
        Assert.notNull(vo,"PlanTeamVo 对象不能为 null");
        return repository.deleteAfterSave(vo);
    }

    public PlanTeam findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deletePhysical(PlanTeamVo vo){
        Assert.notNull(vo,"PlanTeamVo不能为 null");
        List<PlanTeam> entity = repository.findPlanTeamByParms(vo);
        for (PlanTeam pt:entity) {
            repository.delete(pt);
        }
    }

    //提供给controller使用的 分页list查询方法
    public Page<PlanTeam> findPage(PlanTeamPageVo PlanTeamPageVo, Pageable pageable){
        return repository.findPage(PlanTeamPageVo,pageable);
    }

    //不分页list查询
    public List<PlanTeam> findList(PlanTeamListVo PlanTeamListVo){
        return repository.findList(PlanTeamListVo);
    }

    //不分页list查询去重的团队ids
    public List<Team> findTeamsByPlanIds(PlanTeamListVo planExpertListVo){
        List<String> planIds =  repository.findTeamsByPlanIds(planExpertListVo.getPlanIds());
        if (!CollectionUtils.isEmpty(planIds)){
            TeamListVo teamListVo = new TeamListVo();
            teamListVo.setTeamIds(planIds);
            return teamRepository.findList(teamListVo);
        }else{
            return null;
        }
    }
}
