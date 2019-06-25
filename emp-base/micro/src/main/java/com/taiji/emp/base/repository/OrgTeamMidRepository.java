package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.OrgTeamMid;
import com.taiji.emp.base.entity.QOrgTeamMid;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 12:56
 */
@Repository
@Transactional(readOnly = true)
public class OrgTeamMidRepository extends BaseJpaRepository<OrgTeamMid,String> {

    /**
     * 根据teamId删除中间表记录
     * @param teamId
     */
    @Transactional
    public void deleteByTeamId(String teamId) {
        List<OrgTeamMid> findResult = findAllByTeamId(teamId);
        super.delete(findResult);

    }

    /**
     * 根据teamId查询该id下的组织机构
     * @param id
     * @return
     */
    public List<OrgTeamMid> findAllByTeamId(String id) {
        QOrgTeamMid orgTeamMid = QOrgTeamMid.orgTeamMid;
        JPQLQuery<OrgTeamMid> query = from(orgTeamMid);

        BooleanBuilder builder = new BooleanBuilder();
        if(!StringUtils.isEmpty(id)){
            builder.and(orgTeamMid.teamId.eq(id));
        }
        List<OrgTeamMid> findResult = findAll(query.where(builder));
        return findResult;
    }

}
