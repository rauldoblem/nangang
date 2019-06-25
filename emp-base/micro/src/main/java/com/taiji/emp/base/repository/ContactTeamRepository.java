package com.taiji.emp.base.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.emp.base.entity.ContactTeam;
import com.taiji.emp.base.entity.QContactTeam;
import com.taiji.emp.base.searchVo.contacts.ContactTeamListVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(
        readOnly = true
)
public class ContactTeamRepository extends BaseJpaRepository<ContactTeam,String> {


    //不带分页信息，查询通讯录组列表
    public List<ContactTeam> findList(ContactTeamListVo contactTeamListVo){
        JPQLQuery<ContactTeam> query = buildQuery(contactTeamListVo);
        return findAll(query);
    }

    private JPQLQuery<ContactTeam> buildQuery(ContactTeamListVo contactTeamListVo){
        QContactTeam contactTeam = QContactTeam.contactTeam;
        JPQLQuery<ContactTeam> query = from(contactTeam);

        BooleanBuilder builder = new BooleanBuilder();
        String teamId = contactTeamListVo.getTeamId();
        String teamName = contactTeamListVo.getTeamName();
        String parentIdStr = contactTeamListVo.getParentIdStr();
        String userId = contactTeamListVo.getUserId();
        if(StringUtils.hasText(teamId)){
            builder.and(contactTeam.id.eq(teamId));
        }
        if(StringUtils.hasText(userId)){
            builder.and(contactTeam.userId.eq(userId));
        }
        if(StringUtils.hasText(teamName)){
            builder.and(contactTeam.teamName.contains(teamName));
        }
        if(StringUtils.hasText(parentIdStr)){
            builder.and(contactTeam.parentId.eq(parentIdStr));
        }
        builder.and(contactTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(
                Projections.bean(ContactTeam.class
                        ,contactTeam.id
                        ,contactTeam.teamName
                        ,contactTeam.parentId
                        ,contactTeam.leaf
                        ,contactTeam.orders
                        ,contactTeam.userId
                )).where(builder);
        return query;
    }

    //根据parentId查询通讯录组列表
    public List<ContactTeam> findListParendId(String parentId){
        JPQLQuery<ContactTeam> query = buildQueryParendId(parentId);
        return findAll(query);
    }

    private JPQLQuery<ContactTeam> buildQueryParendId(String parentId){
        QContactTeam contactTeam = QContactTeam.contactTeam;
        JPQLQuery<ContactTeam> query = from(contactTeam);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(contactTeam.parentId.eq(parentId));
        builder.and(contactTeam.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(ContactTeam.class
                        ,contactTeam.id
                        ,contactTeam.teamName
                        ,contactTeam.parentId
                        ,contactTeam.leaf
                        ,contactTeam.orders
//                        ,contactTeam.status
                )).where(builder).orderBy(contactTeam.orders.desc());
        return query;
    }


    @Override
    @Transactional
    public ContactTeam save(ContactTeam entity){
        Assert.notNull(entity,"ContactTeam 对象不能为 null");
        ContactTeam result;
        if(null == entity.getId()){
            result = super.save(entity);
        }else{
            ContactTeam temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return result;
    }
}
