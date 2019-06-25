package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.OrgTeam;
import com.taiji.emp.base.entity.QOrgTeam;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 11:00
 */
@Repository
@Transactional(readOnly = true)
public class OrgTeamRepository extends BaseJpaRepository<OrgTeam,String>{

    /**
     * id为空（保存）直接保存
     * id不为空（修改），查到该条记录，赋值没改过的属性再保存
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public OrgTeam save(OrgTeam entity){
        Assert.notNull(entity,"OrgTeam对象不能为null");
        OrgTeam result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            OrgTeam temp = findOne(entity.getId());
            Assert.notNull(temp,"没有该OrgTeam分组，不能修改");
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据createUserId 查询该用户建立的所有分组
     * @param createUserId
     * @return
     */
    public List<OrgTeam> findAllByCreateUserId(String createUserId) {
        QOrgTeam orgTeam = QOrgTeam.orgTeam;
        JPQLQuery<OrgTeam> query = from(orgTeam);

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(createUserId)){
            builder.and(orgTeam.createUserId.eq(createUserId))
                    .and(orgTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        }
        query.select(
                Projections.bean(
                        OrgTeam.class
                        , orgTeam.id
                        , orgTeam.teamName
                        , orgTeam.orders
                )
        ).where(builder);
        return findAll(query);
    }

}
