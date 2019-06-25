package com.taiji.emp.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.duty.entity.DutyTeam;
import com.taiji.emp.duty.entity.QDutyTeam;
import com.taiji.emp.duty.vo.DutyTeamVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DutyTeamRepository extends BaseJpaRepository<DutyTeam,String> {

    /**
     * 新增或修改值班人员分组信息
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public DutyTeam save(DutyTeam entity){
        Assert.notNull(entity,"entity对象不能为空");
        DutyTeam result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else{
            DutyTeam temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }


    /**
     * 根据条件查询值班人员分组列表
     * @param orgId
     * @return
     */
    public List<DutyTeam> findList(String orgId) {
        QDutyTeam dutyTeam = QDutyTeam.dutyTeam;

        JPQLQuery<DutyTeam> query = from(dutyTeam);
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(orgId)){
            builder.and(dutyTeam.orgId.eq(orgId));
        }
        builder.and(dutyTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(DutyTeam.class
                ,dutyTeam.id
                ,dutyTeam.teamName
                ,dutyTeam.orgId
                ,dutyTeam.orgName
                ,dutyTeam.isShift
                ,dutyTeam.orderTeam
        )).where(builder).orderBy(dutyTeam.orderTeam.asc());
        return findAll(query);
    }

    /**
     * 根据条件查询值班人员分组列表
     * @param vo
     * @return
     */
    public List<DutyTeam> findDutyList(DutyTeamVo vo) {
        QDutyTeam dutyTeam = QDutyTeam.dutyTeam;

        JPQLQuery<DutyTeam> query = from(dutyTeam);
        BooleanBuilder builder = new BooleanBuilder();
        String orgId = vo.getOrgId();
        List<String> dutyTeamIds = vo.getDutyTeamIds();

        if (StringUtils.hasText(orgId)){
            builder.and(dutyTeam.orgId.eq(orgId));
        }

        if (null != dutyTeamIds && dutyTeamIds.size() > 0){
            builder.and(dutyTeam.id.in(dutyTeamIds));
        }

        builder.and(dutyTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder);
        return findAll(query);
    }

    /**
     * 自动排班的值班组信息
     * @param orgId
     * @return
     */
    public List<DutyTeam> findAutoList(String orgId) {
        QDutyTeam dutyTeam = QDutyTeam.dutyTeam;
        JPQLQuery<DutyTeam> query = from(dutyTeam);
        BooleanBuilder builder = new BooleanBuilder();
        String[] teamNameArray = {"带班领导","值班领导","值班干部"};
        List<String> teamNameList = Arrays.asList(teamNameArray);
        if (StringUtils.hasText(orgId)){
            builder.and(dutyTeam.orgId.eq(orgId));
        }
        builder.and(dutyTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        builder.and(dutyTeam.teamName.in(teamNameList));

        query.select(Projections.bean(DutyTeam.class
                ,dutyTeam.id
                ,dutyTeam.teamName
                ,dutyTeam.orgId
                ,dutyTeam.orgName
                ,dutyTeam.isShift
                ,dutyTeam.orderTeam
        )).where(builder);
        return findAll(query);
    }
}
