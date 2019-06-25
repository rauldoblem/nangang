package com.taiji.emp.base.service;

import com.taiji.emp.base.entity.OrgTeam;
import com.taiji.emp.base.entity.OrgTeamMid;
import com.taiji.emp.base.repository.OrgTeamMidRepository;
import com.taiji.emp.base.repository.OrgTeamRepository;
import com.taiji.emp.base.vo.OrgTeamMidVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 10:59
 */
@Slf4j
@AllArgsConstructor
@Service
public class OrgTeamService {

    @Autowired
    private OrgTeamRepository repository;
    @Autowired
    private OrgTeamMidRepository orgTeamMidRepository;

    public OrgTeam create(OrgTeam entity) {
        Assert.notNull(entity,"entity不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    public OrgTeam update(OrgTeam entity) {
        return repository.save(entity);
    }

    public OrgTeam findOne(String id) {
        return repository.findOne(id);
    }

    @Transactional
    public void deleteOne(String id) {
        //先删除中间表的
        orgTeamMidRepository.deleteByTeamId(id);
        //再删除主表的 逻辑删除
        OrgTeam orgTeam = new OrgTeam();
        orgTeam.setId(id);
        orgTeam.setDelFlag(DelFlagEnum.DELETE.getCode());
        repository.save(orgTeam);
    }

    public List<OrgTeam> findAllByCreateUserId(String createUserId) {
        List<OrgTeam> results = repository.findAllByCreateUserId(createUserId);
        return results;
    }

    public void deleteOrgTeamMidsByTeamId(String teamId) {
        orgTeamMidRepository.deleteByTeamId(teamId);
    }

    public void createOrgTeamMids(List<OrgTeamMid> orgTeamMids) {
        orgTeamMidRepository.save(orgTeamMids);
    }

    public List<OrgTeamMid> searchOrgsByTeamId(String id) {
        return orgTeamMidRepository.findAllByTeamId(id);
    }
}
