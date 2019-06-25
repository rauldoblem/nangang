package com.taiji.emp.res.service;

import com.taiji.emp.res.entity.Team;
import com.taiji.emp.res.repository.TeamRepository;
import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.searchVo.team.TeamPageVo;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TeamService extends BaseService<Team,String> {

    @Autowired
    private TeamRepository repository;

    public Team createOrUpdate(Team entity){
        Assert.notNull(entity,"Team 对象不能为 null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public Team findOne(String id){
        Assert.hasText(id,"id 不能为null或空字符串");
        return repository.findOne(id);
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为null或空字符串!");
        Team entity = repository.findOne(id);
        Assert.notNull(entity,"entity不能为null");
        repository.deleteLogic(entity,delFlagEnum);
    }

    //提供给controller使用的 分页list查询方法
    public Page<Team> findPage(TeamPageVo teamPageVo, Pageable pageable){
        return repository.findPage(teamPageVo,pageable);
    }

    //不分页list查询
    public List<Team> findList(TeamListVo teamListVo){
        return repository.findList(teamListVo);
    }

    /**
     * 通过schemeId救援队伍信息
     * @param schemeId
     * @return
     */
    public List<TeamVo> findBySchemeId(String schemeId) {
        return repository.findBySchemeId(schemeId);
    }
}
